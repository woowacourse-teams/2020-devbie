import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./actions";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    questions: [],
    question: []
  },
  getters: {
    fetchedQuestions(state) {
      return state.questions;
    },
    fetchedQuestion(state) {
      return state.question;
    }
  },
  mutations,
  actions
});
