import { deleteAction, getAction, postAction } from "../../api";

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
    },
    DELETE_NOTICE(state, noticeId) {
      state.notices = state.notices.filter(notice => notice.id !== noticeId);
    }
  },
  actions: {
    async FETCH_NOTICES({ commit }) {
      try {
        const { data } = await getAction(`/api/notices`);
        commit("SET_NOTICES", data["noticeResponses"]);
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
    },
    // eslint-disable-next-line no-unused-vars
    async CREATE_NOTICE({ commit }, noticeRequest) {
      try {
        console.log(noticeRequest);
        await postAction(`/api/notices`, noticeRequest);
      } catch (error) {
        console.log(error);
      }
    },
    async DELETE_NOTICE({ commit }, noticeId) {
      try {
        await deleteAction(`/api/notices/${noticeId}`);
        commit("DELETE_NOTICE", Number(noticeId));
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
