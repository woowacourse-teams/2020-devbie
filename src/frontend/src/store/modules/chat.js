export default {
  state: {
    drawer: false
  },
  mutations: {
    TOGGLE_DRAWER(state) {
      state.drawer = !state.drawer;
    }
  },
  actions: {
    TOGGLE_DRAWER({ commit }) {
      commit("TOGGLE_DRAWER");
    }
  },
  getters: {
    drawer(state) {
      return state.drawer;
    }
  }
};
