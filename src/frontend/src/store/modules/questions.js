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
    },
    CLEAR_HASHTAGS(state) {
      state.question.hashtags = [];
    }
  },
  actions: {
    async FETCH_QUESTIONS({ commit }, orderBy) {
      const { data } = await getAction(`/api/questions?orderBy=${orderBy}`);
      commit("SET_QUESTIONS", data);
    },
    async FETCH_QUESTION({ commit }, questionId) {
      const { data } = await getAction(
        `/api/questions/${questionId}?visit=true`
      );
      commit("SET_QUESTION", data);
    },
    async CREATE_QUESTION({ commit }, request) {
      const response = await postAction("/api/questions", request);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NEW_QUESTION_ID", id);
      return response;
    },
    async UPDATE_QUESTION(state, payload) {
      return await patchAction(`/api/questions/${payload.questionId}`, {
        title: payload.title,
        content: payload.content,
        hashtags: payload.hashtags
      });
    },
    async DELETE_QUESTION(state, questionId) {
      return await deleteAction(`/api/questions/${questionId}`);
    },
    async FETCH_QUESTIONS_BY_HASHTAG({ commit }, hashtag) {
      const { data } = await getAction(`/api/questions?hashtag=${hashtag}`);
      commit("SET_QUESTIONS", data);
    },
    async FETCH_QUESTION_WITHOUT_VISITS({ commit }, questionId) {
      const { data } = await getAction(
        `/api/questions/${questionId}?visit=false`
      );
      commit("SET_QUESTION", data);
    },
    async SEARCH_QUESTIONS({ commit }, { keyword, scope }) {
      const { data } = await getAction(
        `/api/questions?searchBy=${keyword}&scope=${scope}`
      );
      commit("SET_QUESTIONS", data);
    }
  },
  getters: {
    fetchedQuestions(state) {
      return state.questions;
    },
    fetchedQuestion(state) {
      return state.question;
    },
    fetchedQuestionId(state) {
      return state.questionId;
    }
  }
};
