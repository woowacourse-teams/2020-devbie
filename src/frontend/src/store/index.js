import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./actions";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    questions: [],
    question: [],
    questionRecommendation: [],
    questionId: []
  },
  getters: {
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
    }
  },
  mutations,
  actions
});