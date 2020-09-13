import Vue from "vue";
import VueRouter from "vue-router";
import QuestionListView from "../views/question/QuestionListView";
import QuestionDetailView from "../views/question/QuestionDetailView";
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
import NoticeFavoriteView from "../views/favorite/NoticeFavoriteView";
import QuestionFavoriteView from "../views/favorite/QuestionFavoriteView";

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    return err;
  });
};

export const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "main",
      component: NoticeListView
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage
    },
    {
      path: "/notices",
      name: "notices",
      component: NoticeListView,
      props: route => ({
        noticeType: route.query.noticeType,
        jobPosition: route.query.jobPosition,
        keyword: route.query.keyword,
        language: route.query.language
      })
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
      props: route => ({
        id: route.params.id
      }),
      children: [
        {
          path: ":id(\\d+)",
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
    },
    {
      path: "/favorites/notice",
      name: "notice-favorites",
      component: NoticeFavoriteView
    },
    {
      path: "/favorites/question",
      name: "notice-questions",
      component: QuestionFavoriteView
    }
  ]
});

export default router;
