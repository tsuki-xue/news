import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import svgIcon from './components/svgIcon.vue'
// 引入iconfont
import '@/assets/iconFont/iconfont.css'

const app = createApp(App)

app.use(store).use(router).use(ElementPlus).component('svg-icon', svgIcon).mount('#app')

// 自定义指令
app.directive('drag', (el) => {
  const oDiv = el // 获取当前元素
  document.onselectstart = function () {
    return false
  }
  oDiv.onmousedown = function (e) {
    // 鼠标按下，计算当前元素距离可视区的距离
    const disX = e.clientX - oDiv.offsetLeft
    const disY = e.clientY - oDiv.offsetTop
    document.onmousemove = function (e) {
      // 通过事件委托，计算移动的距离
      const l = e.clientX - disX
      const t = e.clientY - disY
      // 移动当前元素
      oDiv.style.left = l + 'px'
      oDiv.style.top = t + 'px'
    }
    document.onmouseup = function (e) {
      document.onmousemove = null
      document.onmouseup = null
    }
    // return false不加的话可能导致黏连，就是拖到一个地方时div粘在鼠标上不下来，相当于onmouseup失效
    return false
  }
})
