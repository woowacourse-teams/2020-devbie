import Vue from "vue";
import VueRouter from "vue-router";
import QuestionListView from "../views/QuestionListView";
import QuestionDetailView from "../views/QuestionDetailView";
import MainPage from "../views/MainPage";
import NoticeMainPage from "../views/NoticeListView";
import LoginPage from "../views/LoginPage";
import QuestionCreateView from "../views/QuestionCreateView";
import QuestionEditView from "../views/QuestionEditView";

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
            path: "/notice",
            name: "notice",
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
        }
    ]
});

export default router;
