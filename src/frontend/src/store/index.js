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
    myQuestionRecommendation: [],
    answers: [],
    answerRecommendation: [],
    myAnswerRecommendation: [],
    questionId: [],
    notices: []
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
    fetchedMyQuestionRecommendation(state) {
      return state.myQuestionRecommendation;
    },
    fetchedAnswers(state) {
      return state.answers;
    },
    fetchedAnswerRecommendation: state => answerId => {
      return state.answerRecommendation.filter(
        ar => ar.answerId === answerId
      )[0];
    },
    fetchedMyAnswerRecommendation: state => answerId => {
      return state.myAnswerRecommendation.filter(
        my => my.answerId === answerId
      )[0];
    },
    fetchedNewCreatedQuestionId(state) {
      return state.questionId;
    },
    fetchedNotices(state) {
      return state.notices;
    }
  },
  mutations,
  actions
});
