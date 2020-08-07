import { getAction, patchAction } from "../../api";

export default {
  state: {
    loginUser: []
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
    async FETCH_LOGIN_USER({ commit }) {
      try {
        const { data } = await getAction("/api/users");
        commit("SET_LOGIN_USER", data);
      } catch (error) {
        console.log(error);
      }
    },
    async UPDATE_USER_INFO({ commit }, payload) {
      try {
        await patchAction(`/api/users/`, payload);
        commit();
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedLoginUser(state) {
      return state.loginUser;
    }
  }
};
