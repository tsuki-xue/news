import axios from '../../util/axios/index'

// 所有数据列表
export default {
  Login (data) {
    return axios({
      method: 'post',
      url: '/api/auth/login',
      data
    })
  },
  newsList (params) {
    return axios({
      method: 'get',
      url: '/api/news/list',
      params
    })
  },
  newsUpdate (data) {
    return axios({
      method: 'post',
      url: '/api/news/update',
      data
    })
  },
  newsDelete (params) {
    return axios({
      method: 'get',
      url: '/api/news/delete',
      params
    })
  },
  newsAddView (params) {
    return axios({
      method: 'get',
      url: '/api/news/addView',
      params
    })
  },
  newsSave (data) {
    return axios({
      method: 'post',
      url: '/api/news/save',
      data
    })
  },
  categoriesList (params) {
    return axios({
      method: 'get',
      url: '/api/categories/list',
      params
    })
  },
  categoriesSave (data) {
    return axios({
      method: 'post',
      url: '/api/categories/save',
      data
    })
  },
  categoriesUpdate (data) {
    return axios({
      method: 'post',
      url: '/api/categories/update',
      data
    })
  },
  categoriesDelete (params) {
    return axios({
      method: 'get',
      url: '/api/categories/delete',
      params
    })
  },
  collect (data) {
    return axios({
      method: 'post',
      url: '/api/collect',
      data
    })
  },
  collectCancel (data) {
    return axios({
      method: 'post',
      url: '/api/collect/cancel',
      data
    })
  }

}
