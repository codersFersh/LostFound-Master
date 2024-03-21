<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入失物名称" v-model="searchParm.lfName"></el-input>
      </el-form-item>
      <el-form-item>
        <!-- <el-input placeholder="请选择类型" v-model="searchParm.lfType"></el-input> -->

        <el-select v-model="ilkData" style="width: 200px" @change="handleSelectChange" placeholder="请选择物品分类">
          <el-option v-for="item in options" :key="item.ilkId" :label="item.ilkName" :value="item.ilkName" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
      </el-form-item>
    </el-form>

    <div :height="TableHeight">
      <el-space wrap="true" size="default">
        <el-row gutter="24" style="width: 100%;">
          <el-col v-for="item in tableList" :key="item.lfId" :span="5" :offset="2" style="padding-bottom: 30px;">
            <el-card :body-style="{ padding: '0px' }"
              style="width: 230px; height: 260px;   box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);"
              shadow="hover">
              <img :src="item.lfImg" class="image" fit="scale-down" />
              <div class="content">
                拾获：{{ item.lfName }}<br /> 地点：{{ item.lfAddress }} <br />时间：{{ item.lfTime }}
                <div class="bottom">
                  <el-button text class="button" style="right: 0 !important; color:blue;"
                    @click="openDrawer(item.lfId)">查看详细</el-button>

                  <el-tag v-if="item.isFound == '0'" type="danger" size="small" effect="Light">待领回</el-tag>
                  <el-tag v-if="item.isFound == '1'" type="warning" size="small" effect="Light">领回确认中</el-tag>
                  <el-tag v-if="item.isFound == '2'" type="success" size="small" effect="Light">已领回</el-tag>


                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-space>
    </div>
    <!-- 分页 -->
    <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="searchParm.currentPage"
      :page-sizes="[20, 40, 80, 100]" :page-size="searchParm.pageSize" layout="total, sizes, prev, pager, next, jumper"
      :total="searchParm.total" background>
    </el-pagination>

    <el-drawer v-model="drawer" size="50%" :show-close="true">
      <el-row>
        <el-col  style="box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19); border-radius: 10px;">
          <el-descriptions title="   招领信息"  size="Large" border style="justify-items: center; align-items: center;" >
          <el-descriptions-item label="识获者：">{{ byIdList.lfUsername }}</el-descriptions-item>
          <el-descriptions-item label="物品：">{{ byIdList.lfName }}</el-descriptions-item>
          <el-descriptions-item label="物品类型：">{{ byIdList.lfType }}</el-descriptions-item>
          <el-descriptions-item label="物品状态：">
            <el-tag v-if="byIdList.isFound == '0'" type="danger" size="small" effect="Light">待领回</el-tag>
            <el-tag v-if="byIdList.isFound == '1'" type="warning" size="small" effect="Light">领回确认中</el-tag>
            <el-tag v-if="byIdList.isFound == '2'" type="success" size="small" effect="Light">已领回</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="拾获时间：">{{ byIdList.lfTime }}</el-descriptions-item>
          <el-descriptions-item label="拾获地址：">{{ byIdList.lfAddress }}</el-descriptions-item>
          <el-descriptions-item label="详细：">{{ byIdList.lfContent }}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions  size="Large" border>     
          <el-descriptions-item label="物品图片：">
            <img :src="byIdList.lfImg" style="width: 300px; height: 300px;" fit="scale-down" />
          </el-descriptions-item>
        </el-descriptions>
       <div class="btnGUI">
        <el-button v-if="!isCurrentUser" type="primary" class="button" @click="addBtn">领回物品</el-button>
       </div>
        </el-col>
      </el-row>
    </el-drawer>

     <!-- 新增编辑 -->
     <SysDialog
      :title="dialog.title"
      :width="dialog.width"
      :height="dialog.height"
      :visible="dialog.visible"
      @on-close="onClose"
      @on-confirm="commit"
    >
      <template v-slot:content>
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="120px"
          :inline="false"
          size="default"
        >
          <el-row>
            <el-col>
              <el-form-item prop="infoPhone" label="联系方式：">
                <el-input v-model="addModel.infoPhone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <el-form-item prop="infoContent" label="领取注明：">
                <el-input v-model="addModel.infoContent" type="textarea"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import { likAll } from '@/api/ilk';
import { foundList, foundById } from '@/api/found';
import { nextTick, onMounted, reactive, ref,computed } from 'vue';
import { userSotre } from '@/store/user';
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import { ElMessage, FormInstance } from "element-plus";
import { AddInfo } from '@/api/info';



//表单ref属性
const addForm = ref<FormInstance>();

//弹框属性
const { dialog, onClose, onShow } = useDialog();

const user = userSotre();


//表格高度
const TableHeight = ref(0);

const drawer = ref(false);

const ilkData = ref([])
interface Item {
  ilkId: string;
  ilkName: string;
}


//新增绑定对象
const addModel = reactive({
  infoId: "",
  userId: "",
  nickName: "",
  infoPhone: "",
  infoContent:"",
  lfId: "",
  isIlk:"",
  lfUserid:"",
  lfName: "",
  lfImg:"",
  lfUsername: "",
  isTrue:"",
});

//表单验证规则
const rules = reactive({
  infoPhone: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入联系方式",
    },
  ],
  infoContent: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请提供证明",
    },
  ],
});

//新增按钮
const addBtn = () => {
  dialog.title = "提交证明";
  dialog.height = 180;
  dialog.width = 500;
  //显示弹框
  onShow();
  //清空表单
  addForm.value?.resetFields();
};




// 搜索框绑定的对象
const searchParm = reactive({
  lfName: "",
  lfType: "",
  currentPage: 1,
  pageSize: 20,
  total: 0,
})


//搜索按钮点击事件
const searchBtn = () => {
  getList()
  ilkData.value = ""
}


const byIdList = reactive({
  lfId:"",
  lfType: "",
  lfName: "",
  lfAddress: "",
  lfImg: "",
  lfContent: "",
  lfUsername: "",
  lfPhone: "",
  isFound: "",
  lfTime: "",
  userId:"",
  isIlk:"",
});

const openDrawer = async (lfId: string) => {
  drawer.value = true;
  let res = await foundById(lfId)
  if (res && res.code == 200) {
    Object.assign(byIdList, res.data);
  }

}

const isCurrentUser = computed(() => {
  return byIdList.userId === user.getUserId || byIdList.isFound === "1";
});


//table数据
const tableList = ref([]);

//获取表格的数据
const getList = async () => {
  let res = await foundList(searchParm);
  if (res && res.code == 200) {
    tableList.value = res.data.records;
    searchParm.total = res.data.total;
  }
}


//页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size;
  getList();
};

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};


//重置按钮点击事件
const resetBtn = () => {
  searchParm.lfName = "";
  searchParm.lfType = "";
  searchParm.currentPage = 1;
  getList()
}

const options = ref<Item[]>([]);

const handleSelectChange = (value: string) => {
  searchParm.lfType = value;
}

// 获取接口数据
const getilkAll = async () => {
  let res = await likAll();
  if (res && res.code === 200) {
    options.value = res.data
  }
}


const commit = () => {
  //验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      addModel.userId = user.getUserId;
      addModel.nickName = user.getNickName;
      addModel.lfId = byIdList.lfId;
      addModel.lfName = byIdList.lfName;
      addModel.lfUserid = byIdList.userId;
      addModel.lfUsername = byIdList.lfUsername;
      addModel.lfImg = byIdList.lfImg;
      addModel.isIlk = byIdList.isIlk;
      let res = await AddInfo(addModel);
      if (res && res.code == 200) {
        ElMessage.success(res.msg); 
        //清空表单
        addForm.value?.resetFields();  
        onClose();
        getList();    
      }
    }
  });
}

onMounted(() => {
  getList()
  getilkAll()
  nextTick(() => {
    TableHeight.value = window.innerHeight - 800
  })
})

</script>

<style scoped>
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  height: 150px;
  display: block;
  border-bottom: 5px solid rgb(54, 51, 51);
}

.content {
  padding: 14px;
  font-size: 13px;
}

.el-row:last-child {
  margin-bottom: 0;
}

:deep(.el-col-offset-2) {
  margin-left: 4%;
}

:deep(.el-space) {
  display: unset;
}

:deep(.el-descriptions__title){
  text-align: center;
  font-size: 25px;
  padding-top: 5px;
  padding-left: 10px;
}

.btnGUI{
  position: relative;
  padding: 10px;
  left: 88%;
  width: 100px;
}
</style>