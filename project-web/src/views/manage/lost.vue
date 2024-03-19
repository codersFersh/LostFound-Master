<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入失物名称" v-model="searchParm.lfName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="ilkData" style="width: 200px" @change="handleSelectChange" placeholder="请选择物品分类">
          <el-option v-for="item in options" :key="item.ilkId" :label="item.ilkName" :value="item.ilkName" />
        </el-select>
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
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" :height="TableHeight" style="width: 100%">
      <el-table-column fixed="left" type="index" width="50" label="序号"  />
      <el-table-column prop="lfUsername" label="失主"  width="100"></el-table-column>
      <el-table-column prop="lfName" label="失物名称" width="100"></el-table-column>
      <el-table-column prop="lfType" label="失物类型" width="100"></el-table-column>
      <el-table-column prop="lfTime" label="丢失时间" width="120"></el-table-column>
      <el-table-column prop="lfImg" label="失物图片" width="150">
        <template #default="{ row }">
          <el-image :src="row.lfImg" style="width: 100px; height: 100px;" />
        </template>
      </el-table-column>
      <el-table-column prop="lfContent" label="详细：" width="180"></el-table-column>
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
      
      <el-table-column prop="isLost" label="失物状态" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.isLost == '0'" type="danger" size="default" effect="Light">
            未寻回
          </el-tag>
          <el-tag v-if="scope.row.isLost == '1'" type="danger" size="default" effect="Light">
            已寻回
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="ispassCause" label="审核备注" width="100"></el-table-column>
      <el-table-column prop="est" label="发布时间" width="200"></el-table-column>
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template #default="scope">
          <el-button type="primary" icon="Edit" size="default"  @click="editBtn(scope.row)">审核</el-button>
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
          <el-row gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item prop="isPass" label="审核状态：">
                <el-select v-model="addModel.isPass" >
                    <el-option label="待审核" value="0"></el-option>
                    <el-option label="不通过" value="1"></el-option>
                    <el-option label="已通过" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item prop="ispassCause" label="审核备注：">
                <el-input v-model="addModel.ispassCause"></el-input>
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
import { ElMessage, FormInstance } from "element-plus";
import useInstance from '@/hooks/useInstance'
import {isPassLostList,LostAdminStatus,LostAdminDel} from '@/api/lost';
import { likAll } from '@/api/ilk';
import { LostStatus } from '@/api/lost/LostModel';

const TableHeight = ref(0);

//获取全局golbal
const { global } = useInstance()

// 弹框属性
const { dialog, onClose, onShow } = useDialog();

// 表单ref属性
const addForm = ref<FormInstance>();

const ilkData = ref([])
interface Item {
  ilkId: string;
  ilkName: string;
}



// 搜索框绑定的对象
const searchParm = reactive({
  lfName: "",
  lfType: "",
  isPass:"",
  isLost:"",
  currentPage: 1,
  pageSize: 20,
  total: 0,
})

//table数据
const tableList = ref([]);
//获取表格的数据
const getList = async () => {
  let res = await isPassLostList(searchParm);
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

const options = ref<Item[]>([]);

const handleSelectChange = (value: string) => {
  searchParm.lfType = value;
}

// 获取接口数据
const getilkAll = async () => {
  let res = await likAll();
  if (res && res.code === 200) {
    options.value = res.data
  }
}

//搜索按钮点击事件
const searchBtn = () => {
  getList()
  ilkData.value = ""
  searchParm.isPass = "";
  searchParm.isLost = "";
}

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};

//重置按钮点击事件
const resetBtn = () => {
  searchParm.lfName = "";
  searchParm.lfType = "";
  searchParm.isPass = "";
  searchParm.isLost = "";
  searchParm.currentPage = 1;
  getList()
}


//新增绑定对象
const addModel = reactive({
  lfId:"",
  isPass:"",
  ispassCause:"",
})

//表单验证规则
const rules = reactive({
  isPass: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请审核",
    },
  ],
});


//编辑按钮
const editBtn = (row:LostStatus) => {
  console.log(row);
  //显示弹框
  dialog.visible = true;
  dialog.title = "编辑";
  dialog.height = 100;
  nextTick(() => {
    //回显数据
    Object.assign(addModel, row);
    addModel.isPass = row.isPass.toString();
  });
  //清空表单
  addForm.value?.resetFields();
};

//提交表单
const commit = () => {
  // 验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      let res  = await LostAdminStatus(addModel);
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
    let res = await LostAdminDel(lfId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      //刷新列表
      getList()
    }
  }
};




onMounted(() => {
  getilkAll()
  getList();
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