<template>
    <div>
        <div ref="lottieBox" :style="{height:setting.screenWidth*0.3+'px',width:setting.screenWidth*0.3+'px'}" class="lottieBox">
          <div class="title">网页走丢了</div>
          <el-button size="large" class="btn" @click="goback"><i class="icon iconfont icon-B--copy-copy"></i>返回首页</el-button>
        </div>
    </div>
</template>

<script setup>
import Lottie from 'lottie-web'
import store from '@/store'
import router from '@/router'
import { onMounted, ref, computed } from 'vue'
import xq from '@/assets/lottie/xq.json'

const lottieBox = ref(null)
const setting = computed(() => {
  return store.state.setting
})
function goback () {
  router.push('/')
}
onMounted(() => {
  Lottie.loadAnimation({
    container: lottieBox.value, // 选择渲染dom
    renderer: 'svg', // 渲染格式
    loop: true, // 循环播放
    autoplay: true, // 是否i自动播放,
    animationData: xq // 渲染动效json
  })
})
</script>

<style lang="less" scoped>
.lottieBox{
    position: fixed;
    left: 50%;
    top:45%;
    transform: translate(-50%,-50%);
}
.title{
  position: absolute;
  bottom: 10%;
  left: 50%;
  transform: translate(-50%,0)
}
.btn{
    position: absolute;
    bottom: -10%;
    left: 50%;
    transform: translate(-50%,0)
}
</style>
