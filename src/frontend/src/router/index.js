import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../components/LoginPage";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/login",
      name: "login",
      component: LoginPage
    }
  ]
});

export default router;
