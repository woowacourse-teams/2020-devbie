import Vue from "vue";
import VueRouter from "vue-router";
import QuestionListView from "../views/question/QuestionListView";
import QuestionDetailView from "../views/question/QuestionDetailView";
import MainPage from "../views/MainPage";
import NoticeMainPage from "../views/notice/NoticeListView";
import LoginPage from "../views/LoginPage";
import QuestionCreateView from "../views/question/QuestionCreateView";
import QuestionEditView from "../views/question/QuestionEditView";
import NoticeDetailView from "../views/notice/NoticeDetailView";
import NoticeDetail from "../components/notice/NoticeDetail";
import HashtagsView from "../views/hashtags/HashtagsView";

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "main",
      component: MainPage
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage
    },
    {
      path: "/notices",
      name: "notices",
      component: NoticeMainPage
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
      path: "/notices",
      name: "notice-detail",
      component: NoticeDetailView,
      children: [
        {
          path: ":id",
          name: "notice-content",
          component: NoticeDetail
        }
      ]
    },
    {
      path: "/hashtags",
      name: "hashtags",
      component: HashtagsView
    }
  ]
});

export default router;
