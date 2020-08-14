import Vue from "vue";
import Vuex from "vuex";
import loginUser from "./modules/loginUser";
import questions from "./modules/questions";
import answers from "./modules/answers";
import notices from "./modules/notices";
import recommendations from "./modules/recommendations";
import hashtags from "./modules/hashtags";

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    loginUser,
    questions,
    answers,
    notices,
    recommendations,
    hashtags
  }
});
