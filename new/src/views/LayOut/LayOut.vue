<template>
  <el-row>
    <el-col :span="data.leftSpan">
      <left-view :isOpen="data.isOpen"></left-view>
    </el-col>
    <el-col :span="data.rightSpan">
      <top-view></top-view>
      <right-view></right-view>
    </el-col>
  </el-row>

</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import LeftView from './LeftView.vue'
import RightView from './RightView.vue'
import TopView from './TopView.vue'
import store from '@/store'

// 获取vuex里的存储的宽度
const Width = computed(() => {
  return store.state.setting.screenWidth
})
const data = reactive({
  leftSpan: 3,
  rightSpan: 21,
  isOpen: false
})

// 监听宽度变化
watch(Width, (nVal, oldVal) => {
  if (nVal > 1000) {
    data.isOpen = false
    data.leftSpan = 3
    data.rightSpan = 21
  } else {
    data.isOpen = true
    data.leftSpan = 2
    data.rightSpan = 22
  }
})

</script>

<style lang="less" scoped>

</style>
