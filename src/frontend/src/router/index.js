import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../components/LoginPage";
import QuestionListView from "../views/QuestionListView";
import QuestionDetailView from "../views/QuestionDetailView";
import QuestionCreateView from "../views/QuestionCreateView";
import QuestionEditView from "../views/QuestionEditView";
import NoticeDetailView from "../views/NoticeDetailView";

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
    },
    {
      path: "/create-question",
      name: "create-question",
      component: QuestionCreateView
    },
    {
      path: "/edit-question/:id",
      name: "edit-question",
      component: QuestionEditView
    },
    {
      path: "/notices/:id",
      name: "notice",
      component: NoticeDetailView
    }
  ]
});
