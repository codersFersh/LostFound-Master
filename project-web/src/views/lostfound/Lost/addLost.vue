<template>
  <el-main class="elMain">
    <el-card shadow="always" style="justify-content: center; align-items: center;">
     <span style="font-family: FangSong;font-weight: 800;">注意请认真核对信息再发布</span>
    </el-card>
    <el-card shadow="always">
    <el-form :model="addModel" ref="addForm" :rules="rules" label-width="120px" :inline="false" size="default">
      <el-row>
      <el-col :span="16">
        <el-row>
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfUsername" label="失主：">
            <el-input v-model="addModel.lfUsername"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfPhone" label="手机号：">
            <el-input v-model="addModel.lfPhone"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row style="padding-top:10px;">
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfName" label="失物名称：">
            <el-input v-model="addModel.lfName"></el-input>
          </el-form-item> 
        </el-col>
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfType" label="失物类型：">
            <el-select v-model="ilkData"  style="width: 240px"  @change="handleSelectChange">
              <el-option v-for="item in options" :key="item.ilkId" :label="item.ilkName" :value="item.ilkName"  />
            </el-select>
          </el-form-item>
        </el-col>       
      </el-row>
      <el-row style="padding-top:10px;">
         <el-col :span="10" :offset="0">
          <el-form-item prop="lfAddress" label="遗失地址：">
            <el-input v-model="addModel.lfAddress"></el-input>
          </el-form-item>
        </el-col>     
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfTime" label="遗失时间：">
            <el-date-picker v-model="addModel.lfTime"type="date"/>
          </el-form-item>
        </el-col>  
      </el-row>   
      <el-row style="padding-top:10px;">
        <el-col :span="10" :offset="0">
          <el-form-item prop="lfContent" label="补充描述：">
            <el-input v-model="addModel.lfContent" type="textarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>   
      <el-row :gutter="20" style="padding-top: 40px;">     
          <el-col :span="10" :offset="0" class="btn">
            <el-button  type="danger" style="width: 25%;" @click="resetLoginForm" plain>重置</el-button>
          </el-col> 
          <el-col :span="10" :offset="0" class="btn">
            <el-button @click="commit"  type="primary" plain style="width: 25%;" >确认发布</el-button>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="8">
        <el-form-item prop="lfImg" label="失物图片：" >
          <el-upload class="avatar-uploader" :show-file-list="false" :on-success="handleSuccess"
                  :before-upload="beforeUpload" :limit="1" list-type="picture-card" :disabled="addModel.lfImg !== ''">
                  <img v-if="addModel.lfImg" :src="addModel.lfImg" class="avatar"
                    style="width: 146px; height: 146px; border-radius: 5px;" />
                  <el-icon v-else class="avatar-uploader-icon">
                    <Plus />
                  </el-icon>                
                </el-upload>
                <div style="padding-left:20px">
                  <!-- 当禁用状态下显示删除按钮 -->
                 <el-button v-if="addModel.lfImg" type="danger" icon="Delete"
                    @click="deleteUploadedImg">删除图片</el-button>                  
                </div>  
              </el-form-item>     
      </el-col>
      </el-row>
    </el-form>
  </el-card>
  </el-main>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { ElMessage, FormInstance } from "element-plus";
import { likAll } from '@/api/ilk';
import { LostAdd } from '@/api/lost';
import { deleteImg, uploadImg } from '@/api/upload';
import type { UploadProps } from 'element-plus'
import useInstance from '@/hooks/useInstance'
import { userSotre } from '@/store/user';

const store = userSotre()

//获取全局golbal
const { global } = useInstance()

const ilkData = ref([])
interface Item {
  ilkId: string;
  ilkName: string;
}
const options = ref<Item[]>([]);

//新增绑定对象
const addModel = reactive({
  lfId: "",
  userId:"",
  lfType: "",
  lfName: "",
  lfAddress: "",
  lfImg: "",
  lfContent: "",
  lfUsername: "",
  lfPhone: "",
  isPass: "",
  ispassCause: "",
  isLost: "",
  lfTime: "",
});

//表单ref属性
const addForm = ref<FormInstance>();

//表单验证规则
const rules = reactive({
  lfName: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入失物名称",
    },
  ],
  lfType: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请选择失物物品类型",
    },
  ],
  lfAddress: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入预计遗失地点",
    },
  ],
  lfImg: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请上传失物图片",
    },
  ],
  lfContent: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请描述失物特点，遗失过程等...",
    },
  ],
  lfUsername: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入您的姓名",
    },
  ],
  lfPhone: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入您的联系方式",
    },
  ],
  lfTime: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请补充遗失大致时间",
    },
  ],

});



// 获取接口数据
const getilkAll = async () => {
    let res = await likAll();
    if (res && res.code === 200) {
      options.value = res.data     
    }
}

const commit = () => {
  //验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      addModel.userId =store.getUserId
      let res = await LostAdd(addModel);
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        resetLoginForm()
        addModel.lfImg ='';
        ilkData.value = "";
      }
    }
  });
}

const resetLoginForm = () => {
  addForm.value?.$el.reset();
  ilkData.value = ""
};


onMounted(()=>{
  getilkAll();
})

const handleSelectChange = (value: string) => {
  addModel.lfType = value;
}


// 处理图片上传成功的回调
const handleSuccess: UploadProps['onSuccess'] = (res, file) => {
  console.log(file)
  if (res && res.code === 200 && res.data) {
    ElMessage.success(res.msg);
    addModel.lfImg = res.data;
  } else {
    ElMessage.error(res ? res.msg : '上传失败，请重试');
  }
}

// 图片上传前的处理逻辑
const beforeUpload: UploadProps['beforeUpload'] = async (file) => {
  // 获取上传文件的后缀名
  const fileExtension = getFileExtension(file.name);

  // 文件格式验证
  if (fileExtension !== 'png' && fileExtension !== 'jpg') {
    ElMessage.error('上传文件格式务必PNG|JPG')
    return false;
  }

  // 文件大小验证
  if (file.size / 1024 / 1024 > 10) {
    ElMessage.error('上传文件大小小于10M')
    return false;
  }

  // 上传文件
  let res = await uploadImg(file);
  if (res && res.code === 200 && res.data) {
    addModel.lfImg = res.data;
    // 返回 false，表示继续上传
    ElMessage.success(res.msg);
    return false;
  } else {
    // 上传失败，弹出错误提示
    ElMessage.error(res ? res.msg : '上传失败，请重试');
    // 返回 false，表示取消上传
    return false;
  }
}

// 获取文件后缀名的函数
const getFileExtension = (filename: any) => {
  return filename.split('.').pop().toLowerCase(); // 转换为小写字母以便比较
}

 // 删除已上传的图片操作
const deleteUploadedImg = async() => { 
  const confirm = await global.$myconfirm('确定删除该数据吗？')
  if (confirm) {
    let filename = addModel.lfImg.substring('http://localhost:8089/img/'.length) // 完全去除"http://localhost:8089/img/"部分
    let res = await deleteImg(filename)
    if (res && res.code == 200) {
      addModel.lfImg ='';
      ElMessage.success(res.msg);
    }
  }
};



</script>

<style scoped>
.elMain{
  justify-content: center;
    align-items: center;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.btn{
  justify-content: center;
  align-items: center;
  padding-left: 200px;
}
</style>