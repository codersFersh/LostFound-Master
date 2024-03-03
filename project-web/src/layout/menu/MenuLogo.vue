<template>
  <div class="logo">
      <img :src="MenuLogo"/>
      <span v-if="show" class="logo-title">{{ title }}</span>
  </div>
</template>

<script setup lang="ts">
import {ref,watch} from 'vue'
import MenuLogo from '@/assets/logo.png'
import {menuStore} from '@/store/menu/index'

//获取store
const store = menuStore()
// const show = computed(()=>{
//   return store.getCollapse
// })

//解决menu伸缩僵硬问题：延时处理
const show = ref(true)
watch(
  ()=>store.getCollapse,
  (collapsed:boolean)=>{
    if(!collapsed){
      setTimeout(()=>{
        show.value = !collapsed
      },300);
    }else{
      show.value = !collapsed;
    }
  }
)

const title = ref(' 失物招领')
</script>

<style scoped lang="scss">
.logo{
  display: flex;
  width: 100%;
  height: 60px;
  background-color: #2b2f3a;
  text-align: center;
  cursor: pointer;
  align-items: center;
  img{
    width: 30px;
    height: 30px;
    margin-right: 10px;
    margin-left: 18px;
  }
}

.logo-title{
  color: #FFF;
  font-weight: 800;
  line-height: 60px;
  font-size: 22px;
  font-family: FangSong;
}
</style>