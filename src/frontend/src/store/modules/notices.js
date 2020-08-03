import { getAction } from "../../api";

export default {
  state: {
    notices: []
  },
  mutations: {
    SET_NOTICES(state, data) {
      state.notices = data;
    }
  },
  actions: {
    async FETCH_NOTICES({ commit }) {
      try {
        const { data } = await getAction(`/api/notices`);
        commit("SET_NOTICES", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedNotices(state) {
      return state.notices;
    }
  }
};
