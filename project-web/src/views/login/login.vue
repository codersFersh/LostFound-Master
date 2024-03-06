<template>
  <!-- 主容器 -->
  <div class="container" :class="{ 'right__panel__active': isRightPanelActive }">
    <!-- 登录表单容器 -->
    <div class="form__container signin__container">
      <!-- 登录表单 -->
      <div style="position: fixed; right: 0;" class="fadeInRight">
        <el-button class="register" type="info" text="立刻注册" @click="toggleRightPanel('register')">立刻注册</el-button>
      </div>
      <el-form :model="loginModel" ref="form" :rules="rules" @keyup.native.enter="handleEnterLogin" class="fadeInRight">
        <h1>用户登录</h1>
        <!--用户名-->
        <el-form-item prop="username">
          <el-input v-model="loginModel.username" prefix-icon="User" placeholder="请输入账号"></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item prop="password">
          <el-input type="password" show-password v-model="loginModel.password" prefix-icon="Lock"
            placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="code" style="padding-left: 110px;">
          <el-row>
            <el-col :span="9" :offset="0">
              <el-input prefix-icon="Loading" placeholder="请输入验证码" v-model="loginModel.code"></el-input>
            </el-col>
            <el-col :span="2" :offset="0">
              <img @click="getImg" :src="imgsrc"
                style="width: 125px; height: 40px; padding-left: 10px; padding-top: 15px;" />
            </el-col>
          </el-row>
        </el-form-item>
        <el-row :gutter="20" class="elrow">
          <el-col :span="12" :offset="0">
            <el-button @click="commit" class="mybtn" type="primary" plain>登录</el-button>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="danger" @click="resetLoginForm" plain>重置</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 注册表单容器 -->
    <div class="form__container signup__container ">
      <img class="drift" alt="" src="@/assets/texting.svg"
        style="position: absolute; left:10px; bottom: 0px; max-width: 300px; max-height: 350px;" />
      <!-- 注册表单 -->
      <el-form :model="addModel" ref="addForm" :rules="addrules" label-width="80px" :inline="false" size="default">
        <h1>创建账户</h1>
        <el-row>
          <el-col :span="12" :offset="0">
            <el-form-item prop="nickName" label="姓名：">
              <el-input v-model="addModel.nickName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-form-item prop="sex" label="性别：">
              <el-radio-group v-model="addModel.sex">
                <el-radio :label="'0'">男</el-radio>
                <el-radio :label="'1'">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="0">
            <el-form-item prop="phone" label="电话：">
              <el-input v-model="addModel.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-form-item prop="email" label="邮箱：">
              <el-input v-model="addModel.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="0">
            <el-form-item prop="username" label="账户：">
              <el-input v-model="addModel.username"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-form-item prop="password" label="密码：">
              <el-input type="password" v-model="addModel.password"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18" :offset="0" style="padding-right: 250px;">
            <el-form-item prop="confirm" label="确定密码">
              <el-input type="password" v-model="addModel.confirm" style="width:168px;"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="width: 100%; left: 170px; padding-top: 20px;">
          <el-col :span="8">
            <el-button type="primary" plain style="width: 100%; height: 35px;" @click="addBtn">注册</el-button>
          </el-col>
        </el-row>
      </el-form>

    </div>
    <!-- 右侧覆盖层容器 -->
    <div class="overlay__container" :style="{ 'clip-path': overlayClipPath, 'left': overlayLeft }">
      <!-- 右侧覆盖层包装 -->
      <div class="overlay__wrapper">
        <!-- 左侧覆盖面板 -->
        <div class="overlay__panel overlay__panel__left">
          <div class="fadeInLeft">
            <h1>失物招领系统</h1>
            <p>输入您的个人信息并与我们一起开始旅程！！</p>
          </div>
          <!-- <el-button class="register" type="info" text="注册账号" @click="toggleRightPanel('register')">注册账号</el-button> -->
          <img class="fadeIn drift" alt="" src="@/assets/11.svg"
            style="position: fixed; left: 18%; bottom: 0px; max-width: 400px; max-height: 500px;" />

        </div>
        <!-- 右侧覆盖面板 -->
        <div class="overlay__panel overlay__panel__right">

          <h1>欢迎您的加入！</h1>
          <p>请认真填写您的个人信息加入我们吧！！！</p>
          <el-button @click="toggleRightPanel('login')" type="info" text="立刻登录" class="loginGo">立刻登录</el-button>
          <img class="drift" alt="" src="@/assets/22.svg"
            style="position: fixed; right: 18%; bottom: 0px; max-width: 400px; max-height: 450px;" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ElMessage, FormInstance } from "element-plus";
import { reactive, ref, onMounted } from "vue";
import { getImgApi, loginApi } from "@/api/user/index";
import { addApi } from "@/api/user/indexUser";
import { userSotre } from '@/store/user/index'
import { useRouter } from "vue-router";

const router = useRouter()
const store = userSotre()

//表单ref属性
const form = ref<FormInstance>();

const addForm = ref<FormInstance>();

//表单验证规则
const rules = reactive({
  username: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入账户",
    },
  ],
  password: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入密码",
    },
  ],
  code: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入验证码",
    },
  ],
});


//注册表单验证规则
const addrules = reactive({
  nickName: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入姓名",
    },
  ],
  sex: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请选择性别",
    },
  ],
  phone: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入电话",
    },
  ],
  username: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入账户",
    },
  ],
  password: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入密码",
    },
  ],
  confirm: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入确定密码",
    },
  ],
});

//新增绑定对象
const addModel = reactive({
  username: "",
  password: "",
  phone: "",
  email: "",
  sex: "",
  nickName: "",
  confirm: ""
});

// 是否激活右侧面板的状态
const isRightPanelActive = ref(true);



// 登录表单模型
const loginModel = reactive({
  username: "admin",
  password: "123",
  code: "",
});



// 处理回车登录逻辑
const handleEnterLogin = (event: KeyboardEvent) => {
  // 如果按下的是回车键且当前处于登录表单中
  if (event.key === 'Enter' && isRightPanelActive.value) {
    commit(); // 调用登录方法
  }
};


const imgsrc = ref("");
//获取验证码
const getImg = async () => {
  let res = await getImgApi();
  if (res && res.code == 200) {
    imgsrc.value = res.data;
  }
};
//登录提交
const commit = () => {
  form.value?.validate(async (valid) => {
    if (valid) {
      let res = await loginApi(loginModel)
      console.log(res);

      if (res && res.code == 200) {
        //存储用户信息
        console.log(res)
        store.setUserId(res.data.userId)
        store.setNickName(res.data.nickName)
        store.setSex(res.data.sex)
        store.setToken(res.data.token)
        //跳转首页
        router.push({ path: '/' })
      }
    }
  })
}

const addBtn = () => {
  addForm.value?.validate(async (valid) => {
    if (valid) {
      //判断新密码和确定密码是否一致
      if (addModel.password != addModel.confirm) {
        ElMessage.warning("密码和确定密码不一致!");
        return;
      }
      let res = null;
      res = await addApi(addModel);
      if (res && res.code == 200) {
        addForm.value?.$el.reset();
        ElMessage.success(res.msg);
      }
    }
  });
}


onMounted(() => {
  getImg();
});

const resetLoginForm = () => {
  form.value?.$el.reset();
};


// 切换右侧面板的显示状态
const overlayClipPath = ref('polygon(0% 0%, 100% 0%, 70% 100%, 0% 100%)');
const overlayLeft = ref('80%'); // 初始化 left 属性

const toggleRightPanel = (panelType: 'register' | 'login') => {
  isRightPanelActive.value = !isRightPanelActive.value;
  overlayClipPath.value = panelType === 'register'
    ? 'polygon(100% 0%, 100% 100%, 0% 100%, 20% 0%)'
    : 'polygon(0% 0%, 100% 0%, 80% 100%, 0% 100%)';
  overlayLeft.value = panelType === 'register' ? '50%' : '80%'; // 更新 left 属性
};

onMounted(() => {
  // 在图片加载完成后，等待两秒后移除fadeIn类，添加drift类
  setTimeout(() => {
    const images = document.querySelectorAll('.fadeIn');
    images.forEach(image => {
      image.classList.remove('fadeIn');
      image.classList.add('drift');
    });
  }, 2000);
});

</script>



<style scoped lang="scss">
.body {
  font-family: "Poppins", sans-serif;
}

.container {
  height: 100vh;
  /* 相对定位，用于内部绝对定位元素的参照 */
  position: relative;
  background-color: gainsboro;
  /* 溢出内容隐藏 */
  overflow: hidden;
}

/* 注册和登录表单容器样式 */
.form__container {
  /* 绝对定位，相对于容器定位 */
  position: absolute;
  width: 60%;
  height: 100%;
  /* 过渡效果 */
  transition: 0.6s ease-in-out;
  border-radius: 15px;
  user-select: none;
  // justify-content: center;
  // align-items: center;


}

/* 注册表单容器样式 */
.signup__container {
  z-index: 2;
}

/* 登录表单容器样式 */
.signin__container {
  /* 初始透明度为0，用于过渡效果 */
  opacity: 0;
  z-index: 1;

}

/* 表单样式 */
.el-form {
  height: 100%;
  max-width: 500px;
  margin: auto;
  /* 使用弹性布局 */
  display: flex;
  /* 垂直方向排列子元素 */
  flex-direction: column;
  /* 水平居中 */
  align-items: center;
  /* 水平居中 */
  justify-content: center;

  //el-input输入框
  :deep(.el-input__wrapper) {
    width: 260px;
    height: 40px;
    border-radius: 15px;
    border-color: transparent;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
  }
}

//按钮
.elrow {
  width: 60%;
}

.mybtn {
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
}

/* 右侧覆盖层容器样式 */
.overlay__container {
  position: absolute;
  top: 0;
  left: 90%;
  height: 100%;
  width: 60%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 10;
}

/* 右侧覆盖层包装样式 */
.overlay__wrapper {
  background: url("@/assets/57-bg.png");
  /* 背景图像居中 */
  background-position: center center;
  /* 背景图像铺满容器 */
  background-size: cover;
  /* 背景图像不重复 */
  background-repeat: no-repeat;
  position: relative;
  left: -150%;
  height: 100%;
  width: 250%;
  transition: transform 0.6s ease-in-out;
}

/* 覆盖面板样式 */
.overlay__panel {
  position: absolute;
  // display: flex; 
  align-items: center;
  justify-content: center;
  /* 垂直方向排列子元素 */
  flex-direction: column;
  // padding: 4rem; 
  text-align: center;
  height: 100%;
  width: 40%;
  transition: transform 0.6s ease-in-out;
  color: white;
  font-size: 1.8rem;
  user-select: none;
  font-weight: 800;
  line-height: 60px;
  font-family: 华文新魏;
}

/* 左侧覆盖面板样式 */
.overlay__panel__left {
  right: 60%;
  transform: translateX(-12%);
}

/* 右侧覆盖面板样式 */
.overlay__panel__right {
  right: 0;
  /* X轴偏移量为0 */
  transform: translateX(0);
}




/* 激活右侧面板时的样式 */
.right__panel__active .overlay__container {
  transform: translateX(-150%);
  /* 水平偏移量为-150% */
}

.right__panel__active .overlay__wrapper {
  transform: translateX(50%);
  /* 水平偏移量为50% */
}

.right__panel__active .overlay__panel__left {
  transform: translateX(25%);
  /* 水平偏移量为25% */
}

.right__panel__active .overlay__panel__right {
  transform: translateX(35%);
  /* 水平偏移量为35% */
}

.right__panel__active .signin__container {

  transform: translateX(66.6%);
  /* 水平偏移量 */
  opacity: 1;
  /* 透明度为1，用于过渡效果 */
  z-index: 5;
  animation: show 0.6s;
  /* 显示动画 */
}

.right__panel__active .signup__container {
  transform: translateX(20%);
  /* 水平偏移量为20% */
  opacity: 0;
  /* 透明度为0，用于过渡效果 */
}

/* 显示动画 */
@keyframes show {

  0%,
  50% {
    opacity: 0;
    /* 透明度为0 */
    z-index: 1;
  }

  51%,
  100% {
    opacity: 1;
    /* 透明度为1 */
    z-index: 5;
  }
}

/* 缩放按钮动画 */
.scale__btn-animation {
  animation: scale-animation 0.6s;
  /* 缩放动画 */
}

/* 缩放动画 */
@keyframes scale-animation {
  0% {
    width: 10rem;
    /* 宽度为10rem */
  }

  50% {
    width: 20rem;
    /* 宽度为20rem */
  }

  100% {
    width: 10rem;
    /* 宽度为10rem */
  }
}


.drift {
  animation: drift 5s ease-in-out infinite;
}

@keyframes drift {
  0% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(-15px);
  }

  100% {
    transform: translateY(0);
  }
}


.fadeIn {
  opacity: 0;
  animation: fadeInAnimation 2s ease-in-out forwards;
}

@keyframes fadeInAnimation {
  from {
    opacity: 0;
    transform: translateY(50px);
    /* 图片下移50像素，模拟从水中涌出效果 */
  }

  to {
    opacity: 1;
    transform: translateY(0);
    /* 图片位置还原 */
  }
}

.fadeInRight {
  opacity: 0;
  animation: fadeInAnimation1 2s ease-in-out forwards;
}

@keyframes fadeInAnimation1 {
  from {
    opacity: 0;
    transform: translateX(100px);
  }

  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.fadeInLeft {
  opacity: 0;
  animation: fadeInAnimation2 2s ease-in-out forwards;
}

@keyframes fadeInAnimation2 {
  from {
    opacity: 0;
    transform: translateX(0);
  }

  to {
    opacity: 1;
    transform: translateX(100px);
  }
}

.register {
  font-weight: 800;
  line-height: 60px;
  font-size: 20px;
  // font-family: FangSong;
  color: blue;
}

.register:hover {
  background-color: rgba(0, 0, 0, 0) !important;
}

.loginGo {
  font-weight: 800;
  line-height: 60px;
  font-size: 20px;
  // font-family: FangSong;
  color: white;
  border: none;
}

.loginGo:hover {
  background-color: rgba(0, 0, 0, 0) !important;
}

:deep(.el-form-item__content) {
  margin-left: 0 !important;
  line-height: 60px;
  font-size: 40px;
}
</style>
