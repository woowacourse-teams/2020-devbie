import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    questions: [],
    question: [],
    questionId: [],
    searchScope: [],
    questionPage: 1,
    questionLastPage: 1000,
    questionKeyword: "",
    questionByHashtag: []
  },
  mutations: {
    SET_QUESTIONS(state, data) {
      state.questionPage = state.questionPage + 1;
      state.questionLastPage = data["lastPage"];
      state.questions = state.questions.concat(data["questions"]);
    },
    SET_QUESTION(state, data) {
      state.question = data;
    },
    SET_NEW_QUESTION_ID(state, data) {
      state.questionId = data;
    },
    CLEAR_HASHTAGS(state) {
      state.question.hashtags = [];
    },
    SET_SEARCH_SCOPE(state, data) {
      state.searchScope = [data];
    },
    SET_KEYWORD(state, data) {
      state.questionKeyword = data;
    },
    INIT_QUESTIONS(state) {
      state.questions = [];
      state.questionPage = 1;
      state.questionLastPage = 1000;
    },
    DELETE_QUESTION(state, data) {
      const idx = state.questions.findIndex(q => q.id === data);
      state.questions.splice(idx, 1);
    }
  },
  actions: {
    async FETCH_QUESTIONS({ commit }, queryUrl) {
      const { data } = await getAction(`/api/questions?` + queryUrl);
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
    async DELETE_QUESTION({ commit }, questionId) {
      const response = await deleteAction(`/api/questions/${questionId}`);
      commit("DELETE_QUESTION", questionId);
      return response;
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
    },
    fetchedSearchScope(state) {
      return state.searchScope;
    },
    fetchedQuestionPage(state) {
      return state.questionPage;
    },
    fetchedQuestionLastPage(state) {
      return state.questionLastPage;
    },
    fetchedQuestionKeyword(state) {
      return state.questionKeyword;
    }
  }
};
