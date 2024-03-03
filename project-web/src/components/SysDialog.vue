<template>
  <el-dialog :model-value="props.visible" :title="props.title" :width="props.width + 'px'" :before-close="onClose"
    append-to-body style="padding: 0 !important;">
    <div style="{height: props.height + 'px'}">
      <slot name="content"></slot>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="danger" @click="onClose">取消</el-button>
        <el-button type="primary" @click="onConfirm">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">

//定义数据类型
interface DialogProps {
  title?: string,
  visible: boolean,
  width?: number,
  height?: number
}

/**
 * 接收父组件传递的数据
 * withDefaults:设置默认值
 * defineProps：父组件传递的参数
 */

//接收父组件传递的数据
const props = withDefaults(defineProps<DialogProps>(), {
  title: '标题',
  visible: false,
  width: 630,
  height: 280
})

//注册事件
const emit = defineEmits(["onClose", "onConfirm"])

//关闭弹窗
const onClose = () => {
  emit('onClose')
}

//确认弹窗
const onConfirm = () => {
  emit('onConfirm')
}

</script>

<style lang="scss" scope>
.container {
  overflow-x: initial;
  overflow-y: auto;
}

.el-dialog {
  border-top-left-radius: 7px !important;
  border-top-right-radius: 7px !important;
  padding: none !important;

  .el-dialog__header {
    margin-right: 0px;
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    background-color:dimgray !important;

    .el-dialog__title {
      color: #fff;
      font-size: 16px;
      font-weight: 600;   
    }
  }

  .el-dialog__headerbtn {
    .el-dialog__close {
      color: #fff;
    }
  }

  .el-dialog__body {
    padding: 10px;
  }

  .el-dialog__footer {
    border-top: 1px solid #e8eaec !important;
    padding: 10px;
  }
}
span.el-dialog__title {
  margin-left: 15px;
}
.el-dialog__header.show-close{
  padding-top: 5px;
}
</style>

