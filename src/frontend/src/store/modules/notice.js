import { getAction } from "../../api";

export default {
  state: {
    notice: []
  },
  mutations: {
    SET_NOTICE(state, data) {
      state.notice = data;
    }
  },
  actions: {
    async FETCH_NOTICE({ commit }, noticeId) {
      try {
        const { data } = await getAction(`/api/notices/${noticeId}`);
        commit("SET_NOTICE", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedNotice(state) {
      return state.notice;
    }
  }
};
