import Vue from "vue";
import Vuex from "vuex";
import loginUser from "./modules/loginUser";
import questions from "./modules/questions";
import answers from "./modules/answers";
import notices from "./modules/notices";
import recommendations from "./modules/recommendations";
import snackBar from "./modules/snackBar";
import hashtags from "./modules/hashtags";
import chat from "./modules/chat";
import favorite from "./modules/favorite";
// import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    loginUser,
    questions,
    answers,
    notices,
    recommendations,
    snackBar,
    hashtags,
    chat,
    favorite
  }

  // plugins: [createPersistedState()]
});
