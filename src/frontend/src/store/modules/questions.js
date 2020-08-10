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
    async UPDATE_QUESTION(state, data) {
      return await patchAction(`/api/questions/${data.id}`, data.request);
    },
    async DELETE_QUESTION(state, questionId) {
      return await deleteAction(`/api/questions/${questionId}`);
    }
  }
};
