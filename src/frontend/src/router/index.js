import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../views/LoginPage";
import MainPage from "../views/MainPage";
import NoticeMainPage from "../views/NoticeMainPage";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "main",
      component: MainPage
    },
    {
      path: "/notice",
      name: "notice",
      component: NoticeMainPage
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage
    }
  ]
});

export default router;
