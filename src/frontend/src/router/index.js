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
import AdminMainView from "../views/admin/AdminMainView";
import NoticeCreateView from "../views/notice/NoticeCreateView";
import NoticeEditView from "../views/notice/NoticeEditView";

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
          path: ":id(\\d+)",
          params: true,
          name: "notice-content",
          component: NoticeDetail
        }
      ]
    },
    {
      path: "/notices/create",
      name: "notice-create",
      component: NoticeCreateView
    },
    {
      path: "/notices/edit/:id",
      name: "notice-edit",
      component: NoticeEditView
    },
    {
      path: "/admin",
      name: "admin",
      component: AdminMainView
    }
  ]
});

export default router;
