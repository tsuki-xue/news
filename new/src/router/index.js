
import { createRouter, createWebHashHistory } from 'vue-router'
import store from '@/store'

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../views/Login/LogIn.vue'),
    meta: {
      title: '登录'
    }
  },
  {
    path: '/:pathMatch(.*)*', // 404页面
    name: 'NotFound',
    component: () => import('../views/NotFound.vue') // 懒加载优化性能
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  // 获取用户路由
  const routers = store.state.permission.userRouter

  // 获取token
  const token = window.localStorage.getItem('token') || ''

  // 存在token
  if (token !== '') {
    // 检测路由是否存在
    if (routers.length === 0) {
      const User = {
        username: '',
        password: '',
        path: ''
      }
      let user = window.localStorage.getItem('user')
      if (user) {
        user = JSON.parse(user)
        User.username = user.username
        User.password = user.password
        User.path = window.location.href.split('#')[1]
        store.dispatch('user/login', User)
      }

      // 路由不存在，取出缓存的地址
      // const routers = window.localStorage.getItem('router') || ''
      // if (routers !== '') {
      //   store.commit('permission/change_userRouter', JSON.parse(routers))
      //   const path = window.location.href.split('#')[1]
      //   // 设置路由并重定向
      //   store.dispatch('permission/SET_ROUTER', path)
      // }
      next()
    } else {
      // 路由存在
      if (to.meta.title && to.meta.title !== '') {
        // 修改页面标题
        document.title = to.meta.title
        // 获取页面tag列表
        const dynamicTags = store.state.permission.dynamicTags
        // 判断页面tag是否已经存在
        const index = dynamicTags.findIndex((item) => item.path === to.path)
        // 若未经存在则加入
        if (index === -1) {
          dynamicTags.push({
            id: dynamicTags.length + 1,
            name: to.name,
            title: to.meta.title,
            path: to.path
          })
          store.commit('permission/change_dynamicTags', dynamicTags)
        }
        // 如果页面需要缓存，则加入缓存列表
        if (to.meta.keepAlive) {
          const index = store.state.permission.needKeepAlive.findIndex((item) => item === to.name)
          if (index === -1) {
            store.commit('permission/change_needKeepAlive', to.name)
          }
        }
      }
      next()
    }
  } else {
    if (to.path === '/') {
      next()
    } else {
      next({ name: 'NotFound' })
    }
  }
})

router.afterEach((to, form, next) => {
  const path = window.location.href.split('#')[1]
  store.commit('user/change_activeDetail', path)
})

// 动态路由
export const dynamicRoutes = []

export default router
