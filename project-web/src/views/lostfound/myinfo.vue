<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入物品名称" v-model="searchParm.lfName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchParm.isIlk" style="width: 150px" placeholder="请选择事件">
                    <el-option label="招领发布" value="0"></el-option>
                    <el-option label="失物发布" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" :height="TableHeight">
      <el-table-column fixed="left" type="index" width="50" label="序号"  />
      <el-table-column prop="isIlk" label="事件"  width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isIlk == '0'" type="danger" size="default" effect="Light">
            招领发布
          </el-tag>
          <el-tag v-if="scope.row.isIlk == '1'" type="info" size="default" effect="Light">
            失物发布
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nickName" label="申请人"></el-table-column>
      <el-table-column prop="lfId" label="事件编号"></el-table-column>
      <el-table-column prop="lfName" label="物品"></el-table-column>
      <el-table-column label="图片">
        <template #default="{ row }">
          <el-image :src="row.lfImg" style="width: 100px; height: 100px;" />
        </template>
      </el-table-column>
      <el-table-column prop="infoPhone" label="联系方式"></el-table-column>
      <el-table-column prop="infoContent" label="申请注明"></el-table-column>
      <el-table-column prop="isTrue" label="状态"  width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isTrue == '0'" type="warning" size="default" effect="Light">
            确认中
          </el-tag>
          <el-tag v-if="scope.row.isTrue == '1'" type="warning" size="default" effect="Light">
            不同意
          </el-tag>
          <el-tag v-if="scope.row.isTrue == '2'" type="warning" size="default" effect="Light">
            同意
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="320" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" size="default" @click="editBtn(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" size="default" @click="deleteBtn(scope.row.infoId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]" :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total" background>
    </el-pagination>

    <!-- 新增编辑 -->
    <SysDialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible"
      @on-close="onClose" @on-confirm="commit">
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="120px" :inline="false" size="default">
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
import SysDialog from '@/components/SysDialog.vue';
import useDialog from "@/hooks/useDialog";
import { reactive, ref, onMounted, nextTick } from 'vue';
import { ElMessage, FormInstance } from "element-plus";
import { EditMyInfoPram } from "@/api/info/infoModel";
import { MyInfoEdit, myinfo,myinfoDel } from "@/api/info";
import { userSotre } from "@/store/user";
import useInstance from '@/hooks/useInstance'

//获取全局golbal
const { global } = useInstance()

const user = userSotre()

const TableHeight = ref(0);


//弹框属性
const { dialog, onClose } = useDialog();

//表单ref属性
const addForm = ref<FormInstance>();

//搜索框绑定的对象
const searchParm = reactive({
  lfName: "",
  isIlk:"",
  UserId:"",
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

//table数据
const tableList = ref([]);
//获取表格的数据
const getList = async () => {
  searchParm.UserId = user.getUserId;
  let res = await myinfo(searchParm);
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

//搜索按钮点击事件
const searchBtn = () => {
  getList()
}

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};

//重置按钮点击事件
const resetBtn = () => {
  searchParm.lfName = ""
  searchParm.currentPage = 1;
  getList()
}

//新增绑定对象
const addModel = reactive({
  infoPhone:"",
  infoContent:"",
})

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



//编辑按钮
const editBtn = (row: EditMyInfoPram) => {
  console.log(row);
  //显示弹框
  dialog.visible = true;
  dialog.title = "编辑";
  dialog.height = 100;
  nextTick(() => {
    //回显数据
    Object.assign(addModel, row);
  });
  //清空表单
  addForm.value?.resetFields();
};

//提交表单
const commit = () => {
  // 验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      let res = await MyInfoEdit(addModel);
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        getList();
        onClose();
      }
    }
  })
}


//删除按钮
const deleteBtn = async (infoId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？')
  console.log(confirm)
  if (confirm) {
    let res = await myinfoDel(infoId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      //刷新列表
      getList()
    }
  }
};






onMounted(() => {
  getList();
  nextTick(()=>{
    TableHeight.value = window.innerHeight - 240
  })
})

</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>