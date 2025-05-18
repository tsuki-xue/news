import axios from 'axios'
import baseUrl from '../config'

import store from '../../store/index'

import { ElMessage, ElMessageBox } from 'element-plus'

axios.defaults.baseURL = baseUrl.baseUrl

axios.interceptors.request.use(function (config) {
  config.headers.Authorization = store.getters.getToken || ''
  config.headers['Content-Type'] = 'application/json'

  return config
}, function (error) {
  return Promise.reject(error)
})

axios.interceptors.response.use(res => {
  // 未设置状态码则默认成功状态
  const code = res.data.code || 200

  if (code === 401) {
    ElMessageBox.confirm('登录状态已过期，请重新登录', '系统提示', { confirmButtonText: '重新登录', cancelButtonText: '取消', type: 'warning' }).then(() => {
      store.commit('user/login_out')
    })

    return new Promise().reject('无效的会话，或者会话已过期，请重新登录。')
  } else {
    return res.data
  }
},
error => {
  let { message } = error
  if (message === 'Network Error') {
    message = '后端接口连接异常'
  } else if (message.includes('timeout')) {
    message = '系统接口请求超时'
  } else if (message.includes('Request failed with status code')) {
    message = '系统接口' + message.substr(message.length - 3) + '异常'
  }
  const status = message.slice(4, 7)
  if (status === 401) {
    ElMessageBox.confirm('登录状态已过期，请重新登录', '系统提示', { confirmButtonText: '重新登录', cancelButtonText: '取消', type: 'warning' }).then(() => {
      store.commit('user/login_out')
    })
  } else {
    ElMessage({ message: message, type: 'error', duration: 5 * 1000 })
  }

  return Promise.reject(error)
}
)

export default axios
