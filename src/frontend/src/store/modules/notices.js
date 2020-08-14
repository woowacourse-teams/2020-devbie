import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    notices: [],
    notice: [],
    noticeType: "JOB",
    jobPosition: "",
    language: "",
    noticeId: ""
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
    async CREATE_NOTICE({ commit }, noticeRequest) {
      try {
        const response = await postAction(`/api/notices`, noticeRequest);
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
    // eslint-disable-next-line no-unused-vars
    async UPLOAD_NOTICE_IMAGE({ commit }, payload) {
      try {
        const response = await postAction(
          `/api/notices/image`,
          payload,
          `content-type: multipart/form-data`
        );
        return response["headers"].location;
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
    }
  }
};
