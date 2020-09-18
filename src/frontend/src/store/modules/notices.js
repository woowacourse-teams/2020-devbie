import { deleteAction, getAction, postAction } from "../../api";

export default {
  state: {
    languages: [],
    jobPositions: []
  },
  mutations: {
    SET_NOTICE_ID(state, data) {
      state.noticeId = data;
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
    async DELETE_NOTICE({ commit }, noticeId) {
      await deleteAction(`/api/notices/${noticeId}`);
      commit("DELETE_NOTICE", Number(noticeId));
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
