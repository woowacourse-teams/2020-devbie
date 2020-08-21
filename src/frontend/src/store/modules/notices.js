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
    jobPositions: []
  },
  mutations: {
    SET_NOTICES(state, data) {
      state.notices = data;
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
    SET_NOTICE_TYPE(state, data) {
      state.noticeType = data;
    },
    SET_JOB_POSITION(state, data) {
      state.jobPosition = data;
    },
    SET_LANGUAGE(state, data) {
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
      const response = await getAction(`/api/notices?` + queryUrl);
      commit("SET_NOTICES", response.data["noticeResponses"]);
      return response;
    },
    async FETCH_NOTICE({ commit }, noticeId) {
      const response = await getAction(`/api/notices/${noticeId}`);
      commit("SET_NOTICE", response.data);
      return response;
    },
    async DELETE_NOTICE({ commit }, noticeId) {
      await deleteAction(`/api/notices/${noticeId}`);
      commit("DELETE_NOTICE", Number(noticeId));
    },
    async EDIT_NOTICE({ commit }, { id, params }) {
      await patchAction(`/api/notices/${id}`, params);
      commit("UPDATE_NOTICE", id, params);
    },
    async FETCH_FILTERS({ commit }) {
      const { data } = await getAction(`/api/notices/filters`);
      commit("SET_FILTERS", data);
    },
    async CREATE_NOTICE({ commit }, noticeRequest) {
      const response = await postAction(`/api/notices`, noticeRequest);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NOTICE_ID", id);
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
    fetchedLanguages(state) {
      return state.languages;
    },
    fetchedJobPositions(state) {
      return state.jobPositions;
    }
  }
};
