import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router/index'

//全局挂载
import myconfirm from './utils/myconfirm'

//国际化
import zhCn from 'element-plus/es/locale/lang/zh-cn'

//引入element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//引入Pinia构造函数
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'

//权限验证
import './permisson'

//按钮权限指令
// import {permission} from './directive/permission'

import hasPerm from './directive/hasPerm'

//echarts
import * as echarts from 'echarts'



// 实例化 Pinia
const pinia = createPinia()

//使用持久化插件
pinia.use(piniaPersist)

const app = createApp(App);

app.config.globalProperties.$echarts = echarts;

// app.directive('permission',permission)

app.config.globalProperties.$hasPerm = hasPerm

app.use(router).use(ElementPlus, {
    locale: zhCn
  }).use(pinia).mount('#app')
//全局注册图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

//全局挂载
app.config.globalProperties.$myconfirm = myconfirm;
