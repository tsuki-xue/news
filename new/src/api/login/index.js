import axios from '../../util/axios/index'

// 所有数据列表
export default {
  Login (data) {
    return axios({
      method: 'post',
      url: '/api/auth/login',
      data
    })
  }
}
