<template>
  <el-main>   
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入失物名称" v-model="searchParm.lfName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input placeholder="请选择类型" v-model="searchParm.lfType"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
      </el-form-item>
    </el-form>

    <el-space wrap>
      <el-row>
        <el-col v-for="item in tableList" :key="item.lfId">
          <el-card :body-style="{ padding: '0px' }">
            <img :src="item.lfImg" class="image" />
            <div style="padding: 14px">
              {{ item.lfName }}
              <div class="bottom">
                <el-button text class="button">操作</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-space>

    <!-- 分页 -->
    <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="searchParm.currentPage"
        :page-sizes="[10, 20, 40, 80, 100]" :page-size="searchParm.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total" background>
    </el-pagination>
  </el-main>
</template>

<script setup lang="ts">
import { lostList } from '@/api/lost';
import { reactive, ref } from 'vue';


//搜索框绑定的对象
const searchParm = reactive({
  lfName:"",
  lfType:"",
  currentPage: 1,
  pageSize: 10,
  total: 0,
})


//搜索按钮点击事件
const searchBtn = () => {
  getList()
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
  display: block;
}
</style>