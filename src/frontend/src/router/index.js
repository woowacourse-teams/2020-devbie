import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../components/LoginPage";
import QuestionView from "../views/QuestionView";

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/login",
      name: "login",
      component: LoginPage
    },
    {
      path: "/questions",
      name: "questions",
      component: QuestionView
    }
  ]
});
