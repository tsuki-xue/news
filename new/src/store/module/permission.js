
import router from '../../router/index'

// const r = require.context('../../views', true, /\.vue/)

const permission = {
  namespaced: true,
  state: {
    userMeta: [],
    userRouter: [],
    needKeepAlive: [],
    dynamicTags: []

  },
  getters: {
  },
  mutations: {
    change_userMeta (state, val) {
      state.userMeta = val
    },
    change_userRouter (state, val) {
      state.userRouter = val
    },
    computed_router (state, val) {
      state.userRouter = val
    },
    change_needKeepAlive (state, val) {
      state.needKeepAlive.push(val)
    },
    change_dynamicTags (state, val) {
      state.dynamicTags = val.filter((item) => item.name !== 'login')
    },
    delete_needKeepAlive (state, val) {
      state.needKeepAlive = val
    },
    delete_dynamicTags (state, tag) {
      const keepIndex = state.needKeepAlive.findIndex((item) => item === tag.name)
      if (keepIndex !== -1) {
        state.needKeepAlive.splice(keepIndex, 1)
      }

      state.dynamicTags.splice(state.dynamicTags.findIndex((item) => item.path === tag.path), 1)
      const length = state.dynamicTags.length - 1
      if (state.dynamicTags.length === 0) {
        router.push('/home')
      } else {
        router.push(state.dynamicTags[length].path)
      }
    }
  },
  actions: {
    addDynamicRoutes ({ commit }, val) {
      // 是高级用户
      let newRouter
      if (val.userLeavel === 1) {
        newRouter = [
          {
            path: '/home',
            component: 'LayOut/LayOut',
            iconCls: 'el-icon-tickets',
            redirect: '/home/pageone',
            meta: {
              title: '新闻',
              id: '0',
              keepAlive: false
            },
            children: [
              {
                path: 'pageone',
                component: 'PageOne/PageOne',
                name: 'PageOne',
                meta: {
                  title: '新闻列表',
                  id: '0-1',
                  keepAlive: true
                }
              }
            ]
          }
        ]
      } else {
        newRouter = [
          {
            path: '/home',
            component: 'LayOut/LayOut',
            iconCls: 'el-icon-tickets',
            redirect: '/home/pageone',
            meta: {
              title: '新闻',
              id: '0',
              keepAlive: false
            },
            children: [
              {
                path: 'pageone',
                component: 'PageOne/PageOne',
                name: 'PageOne',
                meta: {
                  title: '新闻列表',
                  id: '0-1',
                  keepAlive: true
                }
              }, {
                path: 'pagetwo',
                component: 'PageTwo/PageTwo',
                name: 'Pagetwo',
                meta: {
                  title: '收藏列表',
                  id: '0-2',
                  keepAlive: true
                }
              }

            ]
          }
        ]
      }

      const endRouter = JSON.parse(JSON.stringify(newRouter))

      window.localStorage.setItem('router', JSON.stringify(endRouter))

      filterAsyncRouter(newRouter).then((res) => {
        res.forEach((item) => {
          router.addRoute(item)
        })
        commit('change_userRouter', res)

        router.push(val.path === '' ? '/home' : val.path)
      })
    },
    SET_ROUTER ({ state, commit }, val) {
      filterAsyncRouter(state.userRouter).then((res) => {
        res.forEach((item) => {
          router.addRoute(item)
        })
        router.push(val)
      })
    }

  },
  modules: {
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
async function filterAsyncRouter (asyncRouterMap, lastRouter = false, type = false) {
  return new Promise((resolve, reject) => {
    const list = asyncRouterMap.filter(async route => {
      // 如果是compoent
      if (route.component) {
        const rou = JSON.parse(JSON.stringify(route.component))
        route.component = () => import(`../../views/${rou}.vue`)
      }
      if (route.children != null && route.children && route.children.length) {
        route.children = await filterAsyncRouter(route.children, route, type)
      } else {
        delete route.children
        delete route.redirect
      }
      return true
    })
    resolve(list)
  })
}

// const loadView = (view) => {
//   // 循环所有的vue文件进行匹配
//   return new Promise((resolve, reject) => {
//     r.keys().forEach((key, index) => {
//       if (key.includes(view)) {
//         // 导出对应的vue文件
//         resolve(r(key).default)
//       }
//     })
//   })
// }

export default permission
