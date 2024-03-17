<template>
  <div :style="{ height: mianHeight + 'px' }">
    <Carousel class="Sld"></Carousel>
  </div>
</template>

<script setup lang="ts">
import  Carousel  from "@/views/system/Sld/carousel.vue"
import { ref,nextTick, onMounted} from "vue";
const mianHeight = ref(0)


onMounted(() => {
  nextTick(() => {
    mianHeight.value = window.innerHeight - 100
  })
})
</script>

<style scoped>  
.Sld{
  margin-top: 20px;
  margin-left: 20px;
  width: 50%;
  height: 100%;
}
</style>