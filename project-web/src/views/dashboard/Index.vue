<template>
  <el-main :style="{ height: mianHeight + 'px' }">
    <!-- 数量统计 -->
    <el-row
      :gutter="20"
      type="flex"
      justify="center"
      style="margin-bottom: 40px"
    >
      <el-col :span="6">
        <div class="show-header" style="background: rgb(45, 183, 245)">
          <div class="show-num">0</div>
          <div class="bottom-text">用户总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(237, 64, 20)">
          <div class="show-num">0</div>
          <div class="bottom-text">商品总数</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header">
          <div class="show-num">0</div>
          <div class="bottom-text">物资种类</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(255, 153, 0)">
          <div class="show-num">0</div>
          <div class="bottom-text">订单总数</div>
        </div>
      </el-col>
    </el-row>
    <div style="display: flex;">
      <el-card style="flex: 1;">
        <template #header>
          <div class="card-header">
            <span style="color: #000000; font-weight: 600; margin-bottom: 10px">订单统计</span>
          </div>
        </template>
        <div ref="myChart" :style="{ width: '400px', height: '300px' }"></div>
      </el-card>
      <el-card style="margin-left: 20px;flex: 1;">
        <template #header>
          <div class="card-header">
            <span style="color: #000000; font-weight: 600; margin-bottom: 10px">热门商品</span>
          </div>
        </template>
        <div ref="myChart1" :style="{ width: '400px', height: '300px' }"></div>
      </el-card>
      <el-card style="margin-left: 20px;flex: 1;">
        <template #header>
          <div class="card-header">
            <span style="color: #000000; font-weight: 600; margin-bottom: 10px">最受欢迎</span>
          </div>
        </template>
        <div ref="myChart2" :style="{ width: '400px', height: '300px' }"></div>
      </el-card>
    </div>
    <el-card class="box-card" style="margin-top: 30px">
      <div slot="header" class="clearfix">
        <span style="color: #000000; font-weight: 600; margin-bottom: 10px"
          >公告列表</span
        >
        <el-divider></el-divider>
      </div>
      <div class="text item">
        <span style="font-weight: 600; font-size: 14px">放假通知</span
        ><span style="margin-left: 30px; font-size: 14px"
          >放假时间为 2022-01-10放到 2022-3-03-01，大家关好门窗。</span
        >
        <el-divider></el-divider>
      </div>
      <div class="text item">
        <span style="font-weight: 600; font-size: 14px">放假通知</span
        ><span style="margin-left: 30px; font-size: 14px"
          >放假时间为 2022-01-10放到 2022-3-03-01，大家关好门窗。</span
        >
        <el-divider></el-divider>
      </div>
      <div class="text item">
        <span style="font-weight: 600; font-size: 14px">放假通知</span
        ><span style="margin-left: 30px; font-size: 14px"
          >放假时间为 2022-01-10放到 2022-3-03-01，大家关好门窗。</span
        >
        <el-divider></el-divider>
      </div>
    </el-card>
  </el-main>
</template>
<script setup lang='ts'>
import { ref, nextTick, onMounted, reactive } from 'vue'
import useInstance from '@/hooks/useInstance';
const mianHeight = ref(0)
const { global } = useInstance()
const myChart = ref<HTMLElement>();
const myChart1 = ref<HTMLElement>();
const myChart2 = ref<HTMLElement>();

//柱状图
const charts1 = () => {
  //初始化echarts
  const echartInstance = global.$echarts.init(myChart.value);
  //配置项
  let option = reactive({
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar'
      }
    ]
  });
  //通过axios发送请求，获取数据，设置到上面的option的x轴和y轴的数据里面即可
  echartInstance.setOption(option)
}
//饼图
const charts2 = () => {
  const myChart = global.$echarts.init(myChart1.value);
  let option = reactive({
    title: {
      subtext: 'Fake Data',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  });
  //通过axios发送请求，获取数据，设置到上面的option的x轴和y轴的数据里面即可
  myChart.setOption(option)
}
//环图
const charts3 = () => {
  const myChart = global.$echarts.init(myChart2.value);
  let option = reactive({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '40',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ]
      }
    ]
  });
  //通过axios发送请求，获取数据，设置到上面的option的x轴和y轴的数据里面即可
  myChart.setOption(option)
}

onMounted(() => {
  charts1();
  charts2();
  charts3();
  nextTick(() => {
    mianHeight.value = window.innerHeight - 100
  })
})
</script>
<style scoped lang='scss'>
.bottom-text {
  bottom: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.1);
  height: 25px;
  line-height: 25px;
  text-align: center;
  position: absolute;
  font-weight: 600;
}
.show-header {
  background: #00c0ef;
  color: #fff;
  height: 80px;
  border-radius: 5px;
  position: relative;
}
.show-num {
  font-size: 38px;
  font-weight: 600;
  padding: 5px;
}
</style>