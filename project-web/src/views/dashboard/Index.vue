<template>
  <div :style="{ height: mianHeight + 'px' }">
    <Carousel class="Sld"></Carousel>
  </div>
</template>

<script setup lang="ts">
import { sum } from "@/api/lost";
import { userSotre } from "@/store/user";
import  Carousel  from "@/views/system/Sld/carousel.vue"
import { ElNotification } from "element-plus";
import { ref,nextTick, onMounted} from "vue";


const user =  userSotre()

const mianHeight = ref(0)

const Count = async ()=>{
  let a = user.getRoleId;
  if(a !== '14'){
  let res = await sum()
  if(res && res.code == 200){
    let count = res.data
    if(count=='0'){
      let name = user.getNickName
      open1(name)
    }else{
      open(count)
    }
    
  }
  }
}

const open = (count:any) => {
  ElNotification.warning({
    title: '注意您有任务未完成',
    message: `失物管理和招领管理共有 ${count} 件事项未完成`,
    showClose: false,
  })
}

const open1 = (name:any) => {
  ElNotification.success({
    title: 'hello',
    message: `欢迎您，系统管理者，${name} `,
    showClose: false,
  })
}

onMounted(() => {
  Count();
  nextTick(() => {
    mianHeight.value = window.innerHeight - 500
  })
})
</script>

<style scoped>  
.Sld{
  margin-top: 20px;
  margin-left: 20px;
  width: 97%;
  height: 100%;
}
</style>