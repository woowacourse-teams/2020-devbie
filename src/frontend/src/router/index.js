import Vue from "vue";
import VueRouter from "vue-router";
import QuestionListView from "../views/question/QuestionListView";
import QuestionDetailView from "../views/question/QuestionDetailView";
import MainPage from "../views/MainPage";
import NoticeListView from "../views/notice/NoticeListView";
import LoginPage from "../views/LoginPage";
import QuestionCreateView from "../views/question/QuestionCreateView";
import QuestionEditView from "../views/question/QuestionEditView";
import NoticeDetailView from "../views/notice/NoticeDetailView";
import NoticeDetail from "../components/notice/NoticeDetail";
import AdminMainView from "../views/admin/AdminMainView";
import NoticeCreateView from "../views/notice/NoticeCreateView";
import NoticeEditView from "../views/notice/NoticeEditView";
import HashtagsView from "../views/hashtags/HashtagsView";
import MyPageView from "../views/user/MyPageView";

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    window.location.reload();
    return err;
  });
};

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
      component: NoticeListView
    },
    {
      path: "/questions",
      name: "questions",
      component: QuestionListView,
      props: route => ({
        hashtag: route.query.hashtag,
        orderBy: route.query.orderBy,
        title: route.query.title,
        content: route.query.content
      })
    },
    {
      path: "/questions/:id",
      name: "question",
      component: QuestionDetailView
    },
    {
      path: "/question/create",
      name: "create-question",
      component: QuestionCreateView
    },
    {
      path: "/question/edit/:id",
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
      path: "/hashtags",
      name: "hashtags",
      component: HashtagsView
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView
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
