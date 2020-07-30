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
    answers: []
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
    fetchedAnswers(state) {
      return state.answers;
    },
    fetchedAnswer(state) {
      return id => state.answers.find(answer => answer.id === id);
    }
  },
  mutations,
  actions
});
