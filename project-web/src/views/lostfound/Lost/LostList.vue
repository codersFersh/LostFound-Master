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
          <el-col v-for="item in tableList" :key="item.lfId" :span="4" :offset="2" style="padding-bottom: 30px;">
            <el-card :body-style="{ padding: '0px' }"
              style="width: 230px; height: 260px; background-color: beige;  box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);"
              shadow="hover">
              <img :src="item.lfImg" class="image" fit="scale-down" />
              <div class="content">
                丢失：{{ item.lfName }}<br /> 地点：{{ item.lfAddress }} <br />时间：{{ item.lfTime }}
                <div class="bottom">
                  <el-button text class="button" style="right: 0 !important; color:blue;"
                    @click="openDrawer(item.lfId)">查看详细</el-button>

                  <el-tag v-if="item.isLost == '0'" type="danger" size="small" effect="Light">未寻回</el-tag>
                  <el-tag v-if="item.isLost == '1'" type="success" size="small" effect="Light">已寻回</el-tag>


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
        <el-col style="box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19); border-radius: 10px;">
          <el-descriptions title="   失物信息"  size="Large" border style="justify-items: center; align-items: center;" >
          <el-descriptions-item label="失主：">{{ byIdList.lfUsername }}</el-descriptions-item>
          <el-descriptions-item label="失物：">{{ byIdList.lfName }}</el-descriptions-item>
          <el-descriptions-item label="失物类型：">{{ byIdList.lfType }}</el-descriptions-item>
          <el-descriptions-item label="失物状态：">
            <el-tag v-if="byIdList.isLost == '0'" type="danger" size="small" effect="Light">未寻回</el-tag>
                  <el-tag v-if="byIdList.isLost == '1'" type="success" size="small" effect="Light">已寻回</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="丢失时间：">{{ byIdList.lfTime }}</el-descriptions-item>
          <el-descriptions-item label="丢失地址：">{{ byIdList.lfAddress }}</el-descriptions-item>
          <el-descriptions-item label="失物图片：">
            <img :src="byIdList.lfImg" style="width: 300px; height: 300px;" fit="scale-down" />
          </el-descriptions-item>
        </el-descriptions>
       <div class="btnGUI">
        <el-button type="primary" class="button">归还失物</el-button>
       </div>
        </el-col>
      </el-row>
    </el-drawer>
  </el-main>
</template>

<script setup lang="ts">
import { likAll } from '@/api/ilk';
import { lostList, lostById } from '@/api/lost';
import { nextTick, onMounted, reactive, ref } from 'vue';

//表格高度
const TableHeight = ref(0);

const drawer = ref(false);

const ilkData = ref([])
interface Item {
  ilkId: string;
  ilkName: string;
}



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
  lfType: "",
  lfName: "",
  lfAddress: "",
  lfImg: "",
  lfContent: "",
  lfUsername: "",
  lfPhone: "",
  isLost: "",
  lfTime: "",
});

const openDrawer = async (lfId: string) => {
  drawer.value = true;
  let res = await lostById(lfId)
  if (res && res.code == 200) {
    Object.assign(byIdList, res.data);
  }

}


//table数据
const tableList = ref([]);

//获取表格的数据
const getList = async () => {
  let res = await lostList(searchParm);
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