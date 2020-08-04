import { getAction } from "../../api";

export default {
  state: {
    notices: [],
    notice: []
  },
  mutations: {
    SET_NOTICES(state, data) {
      state.notices = data;
    },
    SET_NOTICE(state, data) {
      state.notice = data;
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
    },
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
    fetchedNotices(state) {
      return state.notices;
    },
    fetchedNotice(state) {
      return state.notice;
    }
  }
};
