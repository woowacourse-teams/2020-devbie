import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    notices: [],
    notice: [],
    noticeType: "JOB",
    jobPosition: "",
    language: "",
    noticeId: "",
    languages: [],
    jobPositions: [],
    page: 1,
    lastPage: 1000,
    keyword: ""
  },
  mutations: {
    UPDATE_NOTICES(state, data) {
      state.page = state.page + 1;
      state.lastPage = data["lastPage"];
      state.notices = state.notices.concat(data["noticeResponses"]);
    },
    SET_NOTICE(state, data) {
      state.notice = data;
    },
    SET_NOTICE_ID(state, data) {
      state.noticeId = data;
    },
    DELETE_NOTICE(state, noticeId) {
      state.notices = state.notices.filter(notice => notice.id !== noticeId);
    },
    INIT_NOTICES(state) {
      state.notices = [];
      state.page = 1;
      state.keyword = "";
    },
    SET_NOTICE_TYPE(state, data) {
      this.commit("INIT_NOTICES", this.state);
      state.noticeType = data;
    },
    SET_JOB_POSITION(state, data) {
      this.commit("INIT_NOTICES", this.state);
      state.jobPosition = data;
    },
    SET_LANGUAGE(state, data) {
      this.commit("INIT_NOTICES", this.state);
      state.language = data;
    },
    UPDATE_NOTICE(state, noticeId, data) {
      state.notice = data;
      state.notices = state.notices.map(notice => {
        if (notice.id === noticeId) {
          return {
            ...data,
            id: noticeId
          };
        }
        return notice;
      });
    },
    SET_KEYWORD(state, data) {
      state.notices = [];
      state.page = 1;
      state.keyword = data;
    },
    SET_FILTERS(state, data) {
      const languages = [{ key: "", text: "무관" }];
      state.languages = languages.concat(data.languages.map(res => res.pair));

      const jobPositions = [{ key: "", text: "무관" }];
      state.jobPositions = jobPositions.concat(
        data.jobPositions.map(res => res.pair)
      );
    }
  },
  actions: {
    async FETCH_NOTICES({ commit }, queryUrl) {
      const { data } = await getAction(`/api/notices?` + queryUrl);
      commit("UPDATE_NOTICES", data);
      return data;
    },
    async FETCH_NOTICE({ commit }, noticeId) {
      const { data } = await getAction(`/api/notices/${noticeId}`);
      commit("SET_NOTICE", data);
    },
    async DELETE_NOTICE({ commit }, noticeId) {
      await deleteAction(`/api/notices/${noticeId}`);
      commit("DELETE_NOTICE", Number(noticeId));
    },
    async EDIT_NOTICE({ commit }, { id, params }) {
      await patchAction(`/api/notices/${id}`, params);
      commit("UPDATE_NOTICE", id, params);
    },
    async CREATE_NOTICE({ commit }, noticeRequest) {
      const response = await postAction(`/api/notices`, noticeRequest);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NOTICE_ID", id);
    },
    async FETCH_FILTERS({ commit }) {
      const { data } = await getAction(`/api/notices/filters`);
      commit("SET_FILTERS", data);
    },
    // eslint-disable-next-line no-unused-vars
    async UPLOAD_NOTICE_IMAGE({ commit }, payload) {
      const response = await postAction(
        `/api/notices/image`,
        payload,
        `content-type: multipart/form-data`
      );
      return response["headers"].location;
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
    },
    fetchedNewCreatedNoticeId(state) {
      return state.noticeId;
    },
    fetchedPage(state) {
      return state.page;
    },
    fetchedLastPage(state) {
      return state.lastPage;
    },
    fetchedLanguages(state) {
      return state.languages;
    },
    fetchedJobPositions(state) {
      return state.jobPositions;
    },
    fetchedKeyword(state) {
      return state.keyword;
    }
  }
};
