<template>
  <div class="login_container">
    <img class="svgOne fadeIn drift" alt="" src="@/assets/scrum-board.svg" />
    <img class="svgTwo fadeIn drift" alt="" src="@/assets/easter-egg-hunt.svg" />
    <!-- 这里是用户登录 -->
    <div class="login_box">
      <!--头像区域-->
      <div class="avatar_box">
        <img src="@/assets/logo.png" alt="">
      </div>
      <!--表单-->
      <el-form ref="form" :model="loginModel" :rules="rules" class="login_form" :inline="false" size="large">
        <!--用户名-->
        <el-form-item prop="account">
          <el-input v-model="loginModel.username" prefix-icon="User" placeholder="请输入账号"></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item prop="password">
          <el-input type="password" show-password v-model="loginModel.password" prefix-icon="Lock"
            placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="">
          <el-row :gutter="10">
            <el-col :span="16" :offset="0">
              <el-input prefix-icon="Loading" placeholder="请输入验证码" v-model="loginModel.code"></el-input>
            </el-col>
            <el-col :span="8" :offset="0">
              <!-- <img class="images"  src="@/assets/user.jpg" /> -->
              验证码
            </el-col>
          </el-row>
        </el-form-item>
        <el-row :gutter="20" class="elrow">
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="primary">登录</el-button>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="danger" plain>重置</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { FormInstance } from "element-plus";
import { reactive, ref,onMounted } from "vue";
//表单ref属性
const form = ref<FormInstance>();
//表单绑定对象
const loginModel = reactive({
  username: "",
  password: "",
  code: "",
});
//表单验证规则
const rules = reactive({});


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
.login_container {
  background-image: url('@/assets/57-bg.png');
  background-size: cover; /* 保持背景图片铺满整个容器 */
  // background-position: center; /* 将背景图片放置在容器的中心 */
  height: 100%;
}

.login_box {
  width: 450px;
  height: 350px;
  background-color: #eee;
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
  justify-content: center;
  align-items: center;
  opacity: 0;
  animation: fadeIn1 5s ease-in-out forwards; /* 应用动画 */


  .avatar_box {
    height: 120px;
    width: 120px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    // box-shadow: 0 0 10px #ddd;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
      box-shadow: 0 0 10px #eee;

    }
  }
}

@keyframes fadeIn1 {
  0% {
    opacity: 0;
  }
  10% {
    opacity: 0.1;
  }
  20% {
    opacity: 0.2;
  }
  30% {
    opacity: 0.3;
  }
  40% {
    opacity: 0.4;
  }
  50% {
    opacity: 0.5;
  }
  60% {
    opacity: 0.6;
  }
  70% {
    opacity: 0.7;
  }
  80% {
    opacity: 0.8;
  }
  90% {
    opacity: 0.9;
  }
  100% {
    opacity: 1;
  }
}


.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 20px 30px;
  box-sizing: border-box;

  :deep(.el-input__wrapper) {
    height: 40px;
    border-radius: 15px;
    border-color: transparent;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
  }

}


.inner {
  height: 42px;
  border-radius: 30px !important;
  border-color: transparent;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);

}

.elrow {
  width: 100%;
}

.mybtn {
  width: 100%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .10), 0 0 6px rgba(0, 0, 0, .19);
}

:deep(.el-row.elrow) {
  margin-left: 0px !important;
}

.svgOne {
  position: absolute;
  bottom: 50px;
  left: 0;
  width: 35vw;
}

.svgTwo {
  position: absolute;
  bottom: 50px;
  right: 0;
  width: 30vw;
}

.drift{
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
    transform: translateY(50px); /* 图片下移50像素，模拟从水中涌出效果 */
  }
  to {
    opacity: 1;
    transform: translateY(0); /* 图片位置还原 */
  }
}

</style>
