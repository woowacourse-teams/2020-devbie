import { getAction } from "../../api";

export default {
  state: {
    notices: [],
    notice: [],
    noticeType: "JOB",
    jobPosition: "",
    language: ""
  },
  mutations: {
    SET_NOTICES(state, data) {
      state.notices = data;
    },
    SET_NOTICE(state, data) {
      state.notice = data;
    },
    SET_NOTICE_TYPE(state, data) {
      state.noticeType = data;
    },
    SET_JOB_POSITION(state, data) {
      state.jobPosition = data;
    },
    SET_LANGUAGE(state, data) {
      state.language = data;
    }
  },
  actions: {
    async FETCH_NOTICES({ commit }, queryUrl) {
      const response = await getAction(`/api/notices?` + queryUrl);
      commit("SET_NOTICES", response.data);
    },
    async FETCH_NOTICE({ commit }, noticeId) {
      const response = await getAction(`/api/notices/${noticeId}`);
      commit("SET_NOTICE", response.data);
    }
  },
  getters: {
    fetchedNotices(state) {
      return state.notices;
    },
    fetchedNotice(state) {
      return state.notice;
    },
    fetchedNoticeType(state) {
      return state.noticeType;
    },
    fetchedLanguage(state) {
      return state.language;
    },
    fetchedJobPosition(state) {
      return state.jobPosition;
    }
  }
};
