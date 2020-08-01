import { getAction, postAction, patchAction, deleteAction } from "../api";

export default {
  async FETCH_LOGIN_USER({ commit }) {
    try {
      const { data } = await getAction("/api/users");
      commit("SET_LOGIN_USER", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_QUESTIONS({ commit }) {
    try {
      const { data } = await getAction("/api/questions");
      commit("SET_QUESTIONS", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_QUESTION({ commit }, questionId) {
    try {
      const { data } = await getAction(`/api/questions/${questionId}`);
      commit("SET_QUESTION", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_QUESTION_RECOMMENDATION({ commit }, questionId) {
    try {
      const { data } = await getAction(
        `/api/recommendation-question?objectId=${questionId}`
      );
      commit("SET_QUESTION_RECOMMENDATION", data);
    } catch (error) {
      console.log(error);
    }
  },
  async CREATE_QUESTION({ commit }, request) {
    try {
      const response = await postAction("/api/questions", request);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NEW_QUESTION_ID", id);
    } catch (error) {
      console.log(error);
    }
  },
  async UPDATE_QUESTION({ commit }, payload) {
    try {
      await patchAction(`/api/questions/${payload.id}`, payload.request);
      commit();
    } catch (error) {
      console.log(error);
    }
  },
  async DELETE_QUESTION({ commit }, questionId) {
    try {
      await deleteAction(`/api/questions/${questionId}`);
      commit();
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_ANSWERS({ commit }, questionId) {
    try {
      const { data } = await getAction(`/api/answers?questionId=${questionId}`);
      commit("SET_ANSWERS", data.answerResponses);
    } catch (error) {
      console.log(error);
    }
  },
  async DELETE_ANSWER({ commit }, answerId) {
    try {
      await deleteAction(`/api/answers/${answerId}`).then(() => {
        commit("DELETE_ANSWER", answerId);
      });
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_NOTICES({ commit }) {
    try {
      const { data } = await getAction(`/api/notices`);
      commit("SET_NOTICES", data);
    } catch (error) {
      console.log(error);
    }
  }
};
