import { createStore } from 'vuex'
import setting from './module/setting'
import user from './module/user'
import permission from './module/permission'
export default createStore({
  namespaced: true,
  state: {
    // 基础url
    BaseUrl: 'http://192.168.0.236:8448',
    // wsurl
    wsUrl: ''
  },
  getters: {
    // 获得token
    getToken (state, val) {
      return window.localStorage.getItem('token')
    }
  },
  mutations: {
    // 设置token
    setToken (state, val) {
      window.localStorage.setItem('token', val)
    }
  },
  actions: {
  },
  modules: {
    setting,
    user,
    permission
  }
})
