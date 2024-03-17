<template>
  <el-main>
    <!-- 搜索 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item>
        <el-input placeholder="请输入物品类型名称" v-model="searchParm.ilkName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="searchBtn">搜索</el-button>
        <el-button icon="Close" type="danger" @click="resetBtn">重置</el-button>     
        <el-button icon="Plus" type="primary"  v-if="global.$hasPerm(['sys:ilk:add'])" @click="addBtn">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableList" :height="TableHeight">
      <el-table-column prop="ilkId" label="物品编号"></el-table-column>
      <el-table-column prop="ilkName" label="类型名称"></el-table-column>
      <el-table-column label="操作" width="320" align="center">
        <template #default="scope" v-if="global.$hasPerm(['sys:ilk:edit','sys:ilk:delete'])">
          <el-button type="primary" icon="Edit" size="default" v-if="global.$hasPerm(['sys:ilk:edit'])" @click="editBtn(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" size="default" v-if="global.$hasPerm(['sys:ilk:delete'])" @click="deleteBtn(scope.row.ilkId)">删除</el-button>
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
              <el-form-item prop="ilkName" label="类型名称：">
                <el-input v-model="addModel.ilkName"></el-input>
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
import { Sld } from '@/api/sld/SldModel';
import useInstance from '@/hooks/useInstance'
import { AddIlk, DelIlk, EditIlk, ListIlk } from '@/api/ilk';

const TableHeight = ref(0);

//获取全局golbal
const { global } = useInstance()

//弹框属性
const { dialog, onClose, onShow } = useDialog();

//表单ref属性
const addForm = ref<FormInstance>();

//搜索框绑定的对象
const searchParm = reactive({
  ilkName: "",
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

//table数据
const tableList = ref([]);
//获取表格的数据
const getList = async () => {
  let res = await ListIlk(searchParm);
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
}

//页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page;
  getList();
};

//重置按钮点击事件
const resetBtn = () => {
  searchParm.ilkName = ""
  searchParm.currentPage = 1;
  getList()
}

//新增绑定对象
const addModel = reactive({
  ilkId: "",
  ilkName: "",
})

//表单验证规则
const rules = reactive({
  ilkName: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请输入物品类型名称",
    },
  ],
});

//判断新增还是编辑的标识 0:新增 1：编辑
const tags = ref("");

//新增按钮
const addBtn = () => {
  tags.value = "0";
  dialog.title = "新增";
  dialog.height = 100;
  //显示弹窗
  onShow();
  //清空表单
  addForm.value?.resetFields();
}

//编辑按钮
const editBtn = (row: Sld) => {
  tags.value = "1";
  console.log(row);
  //显示弹框
  dialog.visible = true;
  dialog.title = "编辑";
  dialog.height = 100;
  nextTick(() => {
    //回显数据
    Object.assign(addModel, row);
  });
  //清空表单
  addForm.value?.resetFields();
};

//提交表单
const commit = () => {
  // 验证表单
  addForm.value?.validate(async (valid) => {
    if (valid) {
      let res = null;
      if (tags.value == "0") {
        res = await AddIlk(addModel);
      } else {
        res = await EditIlk(addModel);
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        getList();
        onClose();
      }
    }
  })
}


//删除按钮
const deleteBtn = async (sldId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？')
  console.log(confirm)
  if (confirm) {
    let res = await DelIlk(sldId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      //刷新列表
      getList()
    }
  }
};




onMounted(() => {
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