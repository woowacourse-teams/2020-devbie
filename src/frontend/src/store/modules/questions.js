import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    questions: [],
    question: [],
    questionId: []
  },
  mutations: {
    SET_QUESTIONS(state, data) {
      state.questions = data;
    },
    SET_QUESTION(state, data) {
      state.question = data;
    },
    SET_NEW_QUESTION_ID(state, data) {
      state.questionId = data;
    }
  },
  actions: {
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
    async CREATE_QUESTION({ commit }, request) {
      const response = await postAction("/api/questions", request);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NEW_QUESTION_ID", id);
      return response;
    },
    async UPDATE_QUESTION({ commit }, payload) {
      const response = await patchAction(
        `/api/questions/${payload.id}`,
        payload.request
      );
      commit();
      return response;
    },
    async DELETE_QUESTION({ commit }, questionId) {
      try {
        await deleteAction(`/api/questions/${questionId}`);
        commit();
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedQuestions(state) {
      return state.questions;
    },
    fetchedQuestion(state) {
      return state.question;
    },
    fetchedNewCreatedQuestionId(state) {
      return state.questionId;
    }
  }
};
