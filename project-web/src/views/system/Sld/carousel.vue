<template>
  <div class="carousel">
    <el-carousel height="500px" direction="vertical" :autoplay="true" :interval="3000">
      <el-carousel-item v-for="item in sldImgs" :key="item">
        <img :src="item" class="imgSrc"/>
      </el-carousel-item>
    </el-carousel>
  </div>  
</template>

<script setup lang="ts">
import { listFiring } from '@/api/sld';
import { reactive, onMounted, ref } from 'vue';



const searchParm = reactive({
  sldName: "",
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

const tableList = ref([]);
const sldImgs = ref<string[]>([]);


const getList = async () => {
  let res = await listFiring(searchParm);
  if (res && res.code === 200) {
    tableList.value = res.data.records;
    sldImgs.value = tableList.value.map(item => item.sldImg).filter(Boolean);
    console.log(sldImgs.value);
  }
}

onMounted(() => {
  getList();
})
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item img {
  width: 100%;
  height: 100%;
  /* object-fit: contain; */
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.carousel {
  width: auto;
  height: auto;
  /* border-radius: 15px !important; */
}
:deep(.el-carousel--vertical){
  border-radius: 15px;
}
</style>
