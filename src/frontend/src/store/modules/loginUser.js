import { getAction } from "../../api";

export default {
  state: {
    isLoggedIn: false,
    loginUser: {}
  },
  mutations: {
    SET_LOGIN_USER(state, data) {
      state.loginUser = data;
      state.isLoggedIn = true;
    },
    DELETE_LOGIN_USER(state) {
      state.loginUser = {};
      state.isLoggedIn = false;
    }
  },
  actions: {
    async FETCH_LOGIN_USER({ commit }) {
      try {
        const { data } = await getAction("/api/users");
        commit("SET_LOGIN_USER", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedLoginUser(state) {
      return state.loginUser;
    },
    isLoggedIn(state) {
      return state.isLoggedIn;
    }
  }
};
