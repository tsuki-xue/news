import login from '@/api/login'
import store from '../index'
import { ElMessage } from 'element-plus'
import router from '@/router'

const user = {
  namespaced: true,
  state: {
    userDetail: '',
    userLeavel: '',
    activeDetail: ''
  },
  getters: {

  },
  mutations: {

    change_userDetail (state, val) {
      state.userDetail = val
      window.localStorage.setItem('user', JSON.stringify(val))
    },
    change_userLeavel (state, val) {
      state.userLeavel = val
    },
    change_userMeta (state, val) {
      state.userMeta = val
    },
    change_activeDetail (state, val) {
      state.activeDetail = val
    },
    login_out (state, val) {
      state.userDetail = ''
      state.userLeavel = ''
      state.activeDetail = ''
      window.localStorage.removeItem('user')
      window.localStorage.removeItem('token')
      window.localStorage.removeItem('router')
      store.commit('permission/delete_needKeepAlive', [])
      store.commit('permission/change_dynamicTags', [])
      router.push('/')
    }
  },
  actions: {
    login ({ state }, val) {
      login.Login(val).then(res => {
        if (res.code === 200) {
          const userLeave = res.data.user === 'admin' ? 1 : 2
          const path = val.path
          state.userDetail = res.data.user
          state.userLeavel = userLeave
          window.localStorage.setItem('token', res.data.token)
          store.dispatch('permission/addDynamicRoutes', {
            userLeavel: userLeave,
            path
          })
          if (path === '') {
            ElMessage.success({
              message: '登录成功，欢迎回来！'
            })
          }
        } else {
          ElMessage.error({
            message: res.msg
          })
        }
      })
    }
  },
  modules: {
  }
}

export default user
