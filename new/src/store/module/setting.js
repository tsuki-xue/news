const setting = {
  namespaced: true,
  state: {
    screenHeight: '',
    screenWidth: '',
    status: false
  },
  getters: {
  },
  mutations: {
    changewh (state, val) {
      state.screenHeight = window.innerHeight
      state.screenWidth = window.innerWidth
    },
    change_status (state, val) {
      state.status = !state.status
    }
  },
  actions: {
  },
  modules: {
  }
}

export default setting
