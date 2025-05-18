<template>
  <router-view></router-view>
</template>

<script setup>

import store from './store'
store.commit('setting/changewh')
window.addEventListener('resize', () => {
  store.commit('setting/changewh')
})

document.title = '新闻'

</script>

<style lang="less">
.icon, .iconfont {
  font-family:"iconfont" !important;
  font-size:16px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}

::-webkit-scrollbar {
  width: 0 !important;
}

::-webkit-scrollbar {
  width: 0 !important;
  height: 0;
}
</style>
