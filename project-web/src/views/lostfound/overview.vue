<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入物品名称" v-model="searchParm.lfName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchParm.isPass" style="width: 150px" placeholder="请选择审核状态">
                    <el-option label="待审核" value="0"></el-option>
                    <el-option label="不通过" value="1"></el-option>
                    <el-option label="已通过" value="2"></el-option>
                </el-select>  
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchParm.isLost" style="width: 150px" placeholder="请选择失物状态">
                    <el-option label="未寻回" value="0"></el-option>
                    <el-option label="已寻回" value="1"></el-option>
                </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchParm.isFound" style="width: 150px" placeholder="请选择招领状态">
                    <el-option label="待领回" value="0"></el-option>
                    <el-option label="已领回" value="1"></el-option>
                </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" :height="TableHeight" style="width: 100%" border>
      <el-table-column fixed="left" type="index" width="50" label="序号"  />
      <el-table-column prop="isIlk" label="事件"  width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isIlk == '0'" type="danger" size="default" effect="Light">
            失物发布
          </el-tag>
          <el-tag v-if="scope.row.isIlk == '1'" type="info" size="default" effect="Light">
            招领发布
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="lfName" label="物品名称" width="100"></el-table-column>
      <el-table-column prop="lfType" label="物品类型" width="100"></el-table-column>
      <el-table-column prop="lfTime" label="时间" width="120"></el-table-column>
      <el-table-column prop="lfImg" label="物品图片" width="150">
        <template #default="{ row }">
          <el-image :src="row.lfImg" style="width: 100px; height: 100px;" />
        </template>
      </el-table-column>
      <el-table-column prop="isPass" label="审核状态" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isPass == '0'" type="info" size="default" effect="Light">
            审核中
          </el-tag>
          <el-tag v-if="scope.row.isPass == '1'" type="danger" size="default" effect="Light">
            不通过
          </el-tag>
          <el-tag v-if="scope.row.isPass == '2'" type="success" size="default" effect="Light">
            已通过
          </el-tag>
        </template>
      </el-table-column>
      
      <el-table-column prop="isLost" label="失物状态"  width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isLost == '0'" type="danger" size="default" effect="Light">
            未寻回
          </el-tag>
          <el-tag v-if="scope.row.isLost == '1'" type="danger" size="default" effect="Light">
            已寻回
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isFound" label="招领状态"  width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isFound == '0'" type="warning" size="default" effect="Light">
            待领回
          </el-tag>
          <el-tag v-if="scope.row.isFound == '1'" type="warning" size="default" effect="Light">
            已领回
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="lfContent" label="详细" width="180"></el-table-column>
      <el-table-column prop="est" label="发布时间"  width="200"></el-table-column>
      <el-table-column prop="mtime" label="修改时间"  width="200"></el-table-column>
      <el-table-column label="操作" width="220" align="center" fixed="right">
        <template #default="scope">
          <el-button type="primary" icon="Edit" size="default"  @click="editBtn(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" size="default" @click="deleteBtn(scope.row.lfId)">删除</el-button>
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
      </template>
    </SysDialog>

  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue';
import useDialog from "@/hooks/useDialog";
import { reactive, ref, onMounted, nextTick } from 'vue';
import { ElMessage, FormInstance,UploadProps } from "element-plus";
import useInstance from '@/hooks/useInstance'
import { MyDel, MyEdit, MyList } from "@/api/my";
import { userSotre } from '@/store/user';
import { MyPram } from '@/api/my/MyModel';
import { likAll } from '@/api/ilk';
import { deleteImg, uploadImg } from '@/api/upload';

const user = userSotre();


const TableHeight = ref(0);

//获取全局golbal
const { global } = useInstance()

// 弹框属性
const { dialog, onClose } = useDialog();

// 表单ref属性
const addForm = ref<FormInstance>();

const options = ref<Item[]>([]);

const ilkData = ref([])
interface Item {
  ilkId: string;
  ilkName: string;
}

const handleSelectChange = (value: string) => {
  addModel.lfType = value;
}



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





// 搜索框绑定的对象
const searchParm = reactive({
  userId:"",
  lfName: "",
  isPass:"",
  isFound:"",
  isLost:"",
  currentPage: 1,
  pageSize: 20,
  total: 0,
})

//table数据
const tableList = ref([]);
//获取表格的数据
const getList = async () => {
  searchParm.userId = user.getUserId
  let res = await MyList(searchParm);
  if (res && res.code == 200) {
    tableList.value = res.data.records;
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
  searchParm.isPass = "";
  searchParm.isLost = "";
  searchParm.isFound = "";
}

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};

//重置按钮点击事件
const resetBtn = () => {
  searchParm.lfName = "";
  searchParm.isPass = "";
  searchParm.isFound = "";
  searchParm.currentPage = 1;
  getList()
}


//新增绑定对象
const addModel = reactive({
  lfId: "",
  lfType: "",
  lfName: "",
  lfAddress: "",
  lfImg: "",
  lfContent: "",
  lfUsername: "",
  lfPhone: "",
  lfTime: "",
})



//编辑按钮
const editBtn = (row:MyPram) => {
  console.log(row);
  //显示弹框
  dialog.visible = true;
  dialog.title = "编辑";
  dialog.width = 900;
  dialog.height = 350;
  // 寻找匹配的物品类型名称
  const matchedItem = options.value.find(item => item.ilkName === row.lfType);
  if (matchedItem) {
    // 将匹配到的物品类型名称赋值给 ilkData
    ilkData.value = matchedItem.ilkName;
  }
  nextTick(() => {
    //回显数据
    Object.assign(addModel, row);
  });
  //清空表单
  addForm.value?.resetFields();
};


// 获取接口数据
const getilkAll = async () => {
    let res = await likAll();
    if (res && res.code === 200) {
      options.value = res.data     
    }
}

//提交表单
const commit = () => {
  // 验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      let res  = await MyEdit(addModel);
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        getList();
        onClose();
      }
    }
  })
}


//删除按钮
const deleteBtn = async (lfId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？')
  console.log(confirm)
  if (confirm) {
    let res = await MyDel(lfId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      //刷新列表
      getList()
    }
  }
};


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




onMounted(() => {
  getList();
  getilkAll();
  nextTick(()=>{
    TableHeight.value = window.innerHeight - 240
  })
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