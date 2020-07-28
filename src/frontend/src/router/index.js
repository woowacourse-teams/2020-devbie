import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../components/LoginPage";
import QuestionListView from "../views/QuestionListView";
import QuestionDetailView from "../views/QuestionDetailView";

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
      component: QuestionListView
    },
    {
      path: "/questions/:id",
      name: "question",
      component: QuestionDetailView
    }
  ]
});
