<template>
  <el-tabs
    v-model="activeTab"
    type="card"
    class="demo-tabs"
    closable
    @tab-remove="removeTab"
    @tab-click="clickBtn"
  >
    <el-tab-pane
      v-for="item in tabList"
      :key="item.path"
      :label="item.title"
      :name="item.path"
    >
    </el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import {computed, ref,watch,onMounted} from 'vue';
import {Tab, tabStore} from '@/store/tabs/index';
import { useRoute } from 'vue-router';
import {useRouter} from 'vue-router';
import { TabsPaneContext } from 'element-plus';

//引入路由
const route = useRoute()
const router = useRouter()

//获取store
const store = tabStore()

//选中的选项卡数据
const activeTab = ref('')

//选项卡的数据
const tabList = computed(()=>{
  return store.getTab
})

//选项卡点击点击事件
const clickBtn =(pane: TabsPaneContext)=>{
  console.log(pane)
  const {props} = pane
  //跳转路由
  router.push({path:props.name as string})
}

//删除
const removeTab = (targetName: string) => {
  //首页不能关闭
  if(targetName === '/dashboard') return;
  //选项卡数据
  const tabs = tabList.value
  //当前激活的的选项卡数据
  let activeName = activeTab.value
  if (activeName === targetName) {
    tabs.forEach((tab:Tab, index:number) => {
      if (tab.path === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1]
        if (nextTab) {
          activeName = nextTab.path
        }
      }
    })
  }

  //设置激活的选项卡
  activeTab.value = activeName
  //重新设置选项卡的数据
  store.tabList = tabs.filter((tab) => tab.path !== targetName)
  //跳转路由
  router.push({path:activeName})
}
//添加选项卡数据
const addTab =()=>{
  const{path,meta} = route;
  const tab:Tab ={
    path:path,
    title:meta.title as string
  }
  store.addTab(tab)
}

//添加选项卡：监听当前路由
watch(
  ()=>route.path,
  ()=>{
    setActiveTab()
    //添加选项卡数据
    addTab()
  }
)
//设置激活选项卡
const setActiveTab = ()=>{
  activeTab.value = route.path
}
onMounted(() => {
  setActiveTab()
  addTab()
})
</script>

<style scoped lang="scss">
:deep(.el-tabs__header) {
  margin: 0px;
}
:deep(.el-tabs__item) {
  height: 26px !important;
  line-height: 26px !important;
  text-align: center !important;
  border: 1px solid #d8dce5 !important;
  border-radius: 5px !important;
  margin: 0px 3px !important;
  color: #495060;
  font-size: 12px !important;
  padding: 0xp 10px !important;
  font-weight: 600 !important;
}
:deep(.el-tabs__nav) {
  border: none !important;
}
:deep(.is-active) {
  border-bottom: 1px solid transparent !important;
  border: 1px solid #42b983 !important;
  background-color: #42b983 !important;
  font-weight: 600 !important;
  color: #fff !important;
}
:deep(.el-tabs__item:hover) {
  color: #495060 !important;
  font-weight: 600 !important;
}
:deep(.is-active:hover) {
  color: #fff !important;
  font-weight: 600 !important;
}
:deep(.el-tabs__nav-next){
  line-height: 26px !important;
}
:deep(.el-tabs__nav-prev){
  line-height: 26px !important;
}
:deep(.el-tabs__header){
  border-bottom: none !important;
}
</style>