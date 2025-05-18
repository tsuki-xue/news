<template>

      <el-menu
    :default-active="user.activeDetail"
    :collapse="props.isOpen"
    @open="handleOpen"
    @close="handleClose"
    @select="selectIt"
    background-color="#334157"
    text-color="#fff"
    active-text-color="#ffd04b"
    :style="{height:settings.screenHeight+'px'}"
  >
    <el-sub-menu :index="item.path" v-for="(item) in routerList" :key="item.meta.id">
      <template #title>
        <el-icon><Location /></el-icon>
        <div class="title">{{ item.meta.title }}</div>
      </template>

        <el-menu-item :index="item.path+'/'+items.path " @click="openItem()"  v-for="(items) in item.children" :key="items.meta.id">
          <div class="title">{{ items.meta.title }}</div>
        </el-menu-item>

    </el-sub-menu>
  </el-menu>

</template>

<script setup>
// 定义props传参
import { computed, defineProps } from 'vue'
import store from '@/store'
// 定义icon
// import { Document, Menu as IconMenu, Location } from '@element-plus/icons-vue'
import { Menu as Location } from '@element-plus/icons-vue'
import router from '@/router'
const props = defineProps({
  isOpen: Boolean
})

const routerList = JSON.parse(window.localStorage.getItem('router'))

const user = computed(() => {
  return store.state.user
})

const settings = computed(() => {
  return store.state.setting
})
const openItem = (e) => {

}

const handleOpen = () => {
}
const handleClose = () => {
}
const selectIt = (e) => {
  router.push(e)
}
</script>

<style lang="less" scoped>
.title{
  text-align: center;
  flex: 1;
}
.title_a{
  text-align: center;
}
</style>
