<template>
  <el-container class="mycontainer">
    <el-aside   width="auto" class="leftmenu">
      <Aside></Aside>
    </el-aside>
    <el-container>
      <el-header class="header">
        <Header></Header>
      </el-header>
      <el-main class="mian">
        <div class="tabs">
          <Tabs class="tabs-content"></Tabs>
          <ColseTabs ></ColseTabs>
        </div>   
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import Aside from '@/layout/menu/MenuBar.vue'
import Header from '@/layout/header/Header.vue'
import Tabs from '@/layout/tabs/Tabs.vue'
import ColseTabs from '@/layout/tabs/ColseTabs.vue'
</script>

<style scoped>
.mycontainer {
  height: 100%;

  .leftmenu {
    background-color: #304156;
  }

  .header {
    background: linear-gradient(to left, #000000, #333333, #555555, #757575, #999999, #b9b9b9, #cccccc, #dcdcdc);
    display: flex;
    align-items: center;
  }

  .mian {
    padding: 0px;
    .tabs{
      display: flex;
      justify-content: space-between;
      flex-grow: 1;
      border-bottom: 1px solid #e4e7ed;
      padding-right: 85px;
      
      .el-tabs{
        width: 100%;
        height: 33px;
      }
      .tabs-content{
        padding-top: 8px;
        padding-left: 10px;
        padding-right: 10px;
      }
    }
  }
}
</style>