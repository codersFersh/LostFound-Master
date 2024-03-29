<template>
  <el-main style="padding-top: 10px;">
    <el-button type="primary" icon="Plus" size="default" @click="addBtn"
    v-if="global.$hasPerm(['sys:menu:add'])"
      >新增</el-button
    >
    <!-- 表格 -->
    <el-table
      style="margin-top: 10px"
      :data="tableList"
      :height="TableHeight"
      row-key="menuId"
      border
      stripe
    >
      <el-table-column label="菜单名称" prop="title"></el-table-column>
      <el-table-column label="菜单图标" prop="icon">
        <template #default="scope">
          <el-icon v-if="scope.row.icon">
            <component :is="scope.row.icon"></component>
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="type">
        <template #default="scope">
          <el-tag v-if="scope.row.type == '0'" type="danger" size="default"
            >目录</el-tag
          >
          <el-tag v-if="scope.row.type == '1'" type="success" size="default"
            >菜单</el-tag
          >
          <el-tag v-if="scope.row.type == '2'" type="primary" size="default"
            >按钮</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="上级菜单" prop="parentName"></el-table-column>
      <el-table-column label="路由名称" prop="name"></el-table-column>
      <el-table-column label="路由地址" prop="path"></el-table-column>
      <el-table-column label="组件路径" prop="url"></el-table-column>
      <el-table-column label="序号" prop="orderNum"></el-table-column>
      <el-table-column v-if="global.$hasPerm(['sys:menu:edit','sys:menu:delete'])" label="操作" align="center" width="220">
        <template #default="scope">
          <el-button
            type="primary"
            icon="Edit"
            size="default"
            v-if="global.$hasPerm(['sys:menu:edit'])"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            type="danger"
            icon="Delete"
            size="default"
            v-if="global.$hasPerm(['sys:menu:delete'])"
            @click="deleteBtn(scope.row.menuId)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增弹框 -->
    <SysDialog
      :title="dialog.title"
      :width="dialog.width"
      :height="dialog.height"
      :visible="dialog.visible"
      @on-close="onClose"
      @on-confirm="conmmit"
    >
      <template v-slot:content>
        <el-form
          :model="addModel"
          ref="addForm"
          :rules="rules"
          label-width="80px"
          :inline="false"
          size="default"
        >
          <el-form-item prop="type" label="菜单类型">
            <el-radio-group v-model="addModel.type">
              <el-radio :label="'0'">目录</el-radio>
              <el-radio :label="'1'">菜单</el-radio>
              <el-radio :label="'2'">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="上级菜单" prop="parentId">
                <el-tree-select
                  @check-change="treeClick"
                  v-model="addModel.parentId"
                  :data="treeList"
                  :render-after-expand="false"
                  show-checkbox
                  check-strictly
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单名称" prop="title">
                <el-input v-model="addModel.title"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="addModel.type != '2'" :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="addModel.icon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="addModel.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单序号" prop="orderNum">
                <el-input v-model="addModel.orderNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="权限字段" prop="code">
                <el-input v-model="addModel.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col v-if="addModel.type != '2'" :span="12" :offset="0">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="addModel.path"></el-input>
              </el-form-item>
            </el-col>
            <el-col v-if="addModel.type == '1'" :span="12" :offset="0">
              <el-form-item label="组件路径" prop="url">
                <el-input v-model="addModel.url" :autosize="{ minRows: 2, maxRows: 4 }"  type="textarea"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from "@/components/SysDialog.vue";
import useDialog from "@/hooks/useDialog";
import { ElMessage, FormInstance } from "element-plus";
import { nextTick, onMounted, reactive, ref } from "vue";
import {
  getParentApi,
  addApi,
  getListApi,
  editApi,
  deleteApi,
} from "@/api/menu/index";
import { MenuType } from "@/api/menu/MenuModel";
import useInstance from "@/hooks/useInstance";
const { global } = useInstance();
//表单ref属性
const addForm = ref<FormInstance>();
//弹框属性
const { dialog, onClose, onShow } = useDialog();

//表格高度
const TableHeight = ref(0);

//获取上级菜单数据
const treeList = ref([]);
const getParent = async () => {
  let res = await getParentApi();
  if (res && res.code == 200) {
    treeList.value = res.data;
  }
};
//标识：区分新增和编辑
const tags = ref("");
//新增按钮
const addBtn = () => {
  tags.value = "0";
  dialog.title = "新增";
  //获取上级菜单
  getParent();
  //显示弹框
  onShow();
  //清空表单
  addForm.value?.resetFields();
};



//表单绑定的对象
const addModel = reactive({
  menuId: "",
  parentId: "",
  title: "",
  code: "",
  name: "",
  path: "",
  url: "",
  type: "",
  icon: "",
  parentName: "",
  orderNum: "",
});
//上级菜单选中事件
const treeClick = (item: any) => {
  console.log(item);
  addModel.parentName = item.title;
};
//表单验证规则
const rules = reactive({
  parentId: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请选择上级菜单",
    },
  ],
  title: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写菜单名称",
    },
  ],
  code: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写权限字段",
    },
  ],
  name: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写路由名称",
    },
  ],
  path: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写路由地址",
    },
  ],
  url: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写组件路径",
    },
  ],
  type: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请选择菜单类型",
    },
  ],
  icon: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写菜单图标",
    },
  ],
  orderNum: [
    {
      required: true,
      trigger: ["blur", "change"],
      message: "请填写菜单序号",
    },
  ],
});
//编辑
const editBtn = async (row: MenuType) => {
  console.log(row);
  tags.value = "1";
  dialog.title = "编辑";
  //获取上级菜单
  await getParent();
  //显示弹框
  onShow();
  //设置回显的数据
  nextTick(() => {
    Object.assign(addModel, row);
  });
  //清空表单
  addForm.value?.resetFields();
};
//删除
const deleteBtn = async (menuId: string) => {
  console.log(menuId);
  const confirm = await global.$myconfirm("确定删除该数据吗?");
  if (confirm) {
    let res = await deleteApi(menuId);
    if (res && res.code == 200) {
      ElMessage.success(res.msg);
      //刷新列表
      getList();
      onClose();
    }
  }
};
//表单提交
const conmmit = () => {
  addForm.value?.validate(async (valid) => {
    if (valid) {
      console.log("验证通过");
      console.log(addModel);
      let res = null;
      if (tags.value == "0") {
        res = await addApi(addModel);
      } else {
        res = await editApi(addModel);
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg);
        //刷新列表
        getList();
        onClose();
      }
    }
  });
};
//获取表格数据
const tableList = ref([]);
const getList = async () => {
  let res = await getListApi();
  if (res && res.code == 200) {
    tableList.value = res.data;
  }
};
onMounted(() => {
  getList();
  nextTick(()=>{
    TableHeight.value = window.innerHeight - 170
  })
});
</script>

<style scoped></style>
