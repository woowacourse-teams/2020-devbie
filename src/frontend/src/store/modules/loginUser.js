import { getAction } from "../../api";

export default {
  state: {
    loginUser: {}
  },
  mutations: {
    SET_LOGIN_USER(state, data) {
      state.loginUser = data;
    },
    DELETE_LOGIN_USER(state) {
      state.loginUser = {};
    }
  },
  actions: {
    FETCH_LOGIN_USER({ commit }) {
      return getAction("/api/users").then(({ data }) => {
        commit("SET_LOGIN_USER", data);
      });
    }
  },
  getters: {
    fetchedLoginUser(state) {
      return state.loginUser;
    }
  }
};
