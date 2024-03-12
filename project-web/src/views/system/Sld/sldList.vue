<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入轮播图名称" v-model="searchParm.sldName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
        <!-- v-if="global.$hasPerm(['sys:user:add'])" -->
        <el-button icon="Plus" type="primary" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList">
      <el-table-column prop="sldName" label="轮播图名称"></el-table-column>
      <el-table-column prop="sldType" label="存储路径"></el-table-column>
      <el-table-column  label="图片">
        <template #default="{row}">
          <el-image  :src="row.sldImg" style="width: 100px; height: 100px;"/>
        </template>
      </el-table-column>
      <el-table-column prop="isEnable" label="是否启用">
        <template #default="scope">
          <el-tag v-if="scope.row.isEnable == '0'" type="danger" size="default" effect="dark">
            未启用
          </el-tag>
          <el-tag v-if="scope.row.isEnable == '1'" type="primary" size="default" effect="dark">
            已启用
          </el-tag>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination @size-change="sizeChange" @current-change="currentChange" :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]" :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="searchParm.total" background>
    </el-pagination>

    <!-- 新增编辑 -->
    <SysDialog :title="dialog.title" :width="dialog.width" :height="dialog.height" :visible="dialog.visible"
      @on-close="onClose" @on-confirm="commit">
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="120px" :inline="false" size="default">
          <el-row gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item prop="sldName" label="图片名称：">
                <el-input v-model="addModel.sldName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item prop="sldType" label="图片描述：">
                <el-input v-model="addModel.sldType"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col span="12" :offset="0">
              <el-form-item label="图片" prop="sldImg">
                <!-- <el-upload class="upload-demo" action="http://localhost:8089/upload/img" :show-file-list="true"
                  :on-success="handleSuccess" :before-upload="beforeUpload" limit="1">
                  <el-button size="small" type="primary">点击上传图片</el-button>
                </el-upload> -->

                <el-upload class="avatar-uploader" :show-file-list="true" :on-success="handleSuccess"
                  :before-upload="beforeUpload" :limit="1" list-type="picture-card">
                  <img v-if="addModel.sldImg" :src="addModel.sldImg" class="avatar" style="width: 58px; height: 58px;" />
                  <el-icon v-else class="avatar-uploader-icon">
                    <Plus />
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>

            <el-col :span="12" :offset="0">
              <el-form-item prop="isEnable" label="轮播状态：">
                <el-radio-group v-model="addModel.isEnable">
                  <el-radio :label="'0'">未启用</el-radio>
                  <el-radio :label="'1'">已启用</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>

  </el-main>
</template>

<script setup lang="ts">
import { SldList, SldAdd } from '@/api/sld';
import { uploadImg } from '@/api/upload';
import type { UploadProps } from 'element-plus'
import SysDialog from '@/components/SysDialog.vue';
import useDialog from "@/hooks/useDialog";
import { reactive, ref, onMounted } from 'vue';
import { ElMessage, FormInstance } from "element-plus";


//弹框属性
const { dialog, onClose, onShow } = useDialog();

//表单ref属性
const addForm = ref<FormInstance>();

//搜索框绑定的对象
const searchParm = reactive({
  sldName: "",
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

//table数据
const tableList = ref([]);
//获取表格的数据
const getList = async () => {
  let res = await SldList(searchParm);
  if (res && res.code == 200) {
    tableList.value = res.data.records;
    // tableList.value.forEach((item: any) => {
    //   item.sldImg=new URL('../../../assets/img/'+item.sldImg, import.meta.url).href
    //   console.log(item.sldImg);
      
    // })
    // console.log(tableList.value);

    // tableList.value = res.data.records.map(item => ({
    //   ...item,
    //   sldImg: item.sldImg ? `http://localhost:8089/img/${item.sldImg}` : ''
    // }));
    
    searchParm.total = res.data.total;
  }
}

//页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size;
  getList();
};

//搜索按钮点击事件
const searchBtn = () => {
  getList()
}

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};

//重置按钮点击事件
const resetBtn = () => {
  searchParm.sldName = ""
  searchParm.currentPage = 1;
  getList()
}

//新增绑定对象
const addModel = reactive({
  sldId: "",
  sldName: "",
  sldType: "",
  isEnable: "",
  sldImg:"",
})

//表单验证规则
const rules = reactive({
  sldName: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入轮播图名称",
    },
  ],
  sldType: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写使用描述",
    },
  ],
  sldImg: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请上传图片",
    },
  ],
});

//新增按钮
const addBtn = () => {
  // tags.value = "0";
  dialog.title = "新增";
  dialog.height = 400;
  //显示弹窗
  onShow();
  //清空表单
  addForm.value?.resetFields();
}

//提交表单
const commit = () => {
  // 验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      let res = await SldAdd(addModel);
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        getList();
        onClose();
      }
    }
  })
}

// const baseUrl = '/img/';

// const getImage = (imageName) => {
//   return baseUrl + imageName;
   
// };

// console.log(getImage());




// 处理图片上传成功的回调
const handleSuccess: UploadProps['onSuccess'] = (res, file) => {
  console.log(file)
  if (res && res.code === 200 && res.data) {
    ElMessage.success(res.msg);
    addModel.sldImg = res.data;
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
    addModel.sldImg = res.data;
    // 返回 false，表示继续上传
    ElMessage.success(res.msg);
  } else {
    // 上传失败，弹出错误提示
    ElMessage.error(res ? res.msg : '上传失败，请重试');
    // 返回 false，表示取消上传
    return false;
  }
}

// 获取文件后缀名的函数
const getFileExtension = (filename:any) => {
  return filename.split('.').pop().toLowerCase(); // 转换为小写字母以便比较
}

onMounted(() => {
  getList();
})

</script>

<style scoped>

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

</style>