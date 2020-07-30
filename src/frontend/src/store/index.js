import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./actions";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    loginUser: [],
    questions: [],
    question: [],
    questionRecommendation: [],
    questionId: [],
    notice: []
  },
  getters: {
    fetchedLoginUser(state) {
      return state.loginUser;
    },
    fetchedQuestions(state) {
      return state.questions;
    },
    fetchedQuestion(state) {
      return state.question;
    },
    fetchedQuestionRecommendation(state) {
      return state.questionRecommendation;
    },
    fetchedNewCreatedQuestionId(state) {
      return state.questionId;
    },
    fetchedNotice(state) {
      return state.notice;
    }
  },
  mutations,
  actions
});
