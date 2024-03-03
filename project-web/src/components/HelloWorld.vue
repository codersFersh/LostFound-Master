<template>
  <h1>{{ store.count }}</h1>
  <h1>{{ count }}</h1>
  <el-button type="primary" size="default" @click="addBtn">新增</el-button>
  <el-icon>
    <search />
  </el-icon>
</template>
<script setup lang="ts">
import { computed } from "vue";
import { testStore } from "@/store/test/index";
// import { storeToRefs } from "pinia";
const store = testStore();

// const { count, msg } = storeToRefs(store);
const count = computed(() => {
  return store.getCount;
});
const addBtn = () => {
  //第一种改变数据的方式
  // store.count++;
  //第二种改变数据的方式
  // store.setCount(++store.count);
  //第三种改变数据的方式
  // store.$patch({
  //   count: ++store.count,
  // });
  //第四种改变数据的方式
  store.$patch((state)=>{
    state.count = ++state.count 
  })
};
</script>

<style scoped></style>