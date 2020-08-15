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
    SET_LANGUAGES(state, data) {
      const languages = [{ key: "", text: "무관" }];
      state.languages = languages.concat(data.map(res => res.pair));
    },
    SET_JOB_POSITIONS(state, data) {
      const jobPositions = [{ key: "", text: "무관" }];
      state.jobPositions = jobPositions.concat(data.map(res => res.pair));
    }
  },
  actions: {
    async FETCH_NOTICES({ commit }, queryUrl) {
      try {
        const { data } = await getAction(`/api/notices?` + queryUrl);
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
    async CREATE_NOTICE({ commit }, noticeRequest) {
      try {
        const temp = {
          ...noticeRequest,
          image: noticeRequest.image.name
        };
        const response = await postAction(`/api/notices`, temp);
        const id = response["headers"].location.split("/")[3];
        commit("SET_NOTICE_ID", id);
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
    },
    async EDIT_NOTICE({ commit }, { id, params }) {
      try {
        await patchAction(`/api/notices/${id}`, params);
        commit("UPDATE_NOTICE", id, params);
      } catch (error) {
        console.log(error);
      }
    },
    async FETCH_LANGUAGES({ commit }) {
      try {
        const { data } = await getAction(`/api/notices/languages`);
        commit("SET_LANGUAGES", data.languages);
      } catch (error) {
        console.log(error);
      }
    },
    async FETCH_JOB_POSITIONS({ commit }) {
      try {
        const { data } = await getAction(`/api/notices/job-positions`);
        commit("SET_JOB_POSITIONS", data.jobPositions);
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
