import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import { router } from "./router";
import { store } from "./store";
import { editor } from "./utils/editor";
import VueAnalytics from "vue-analytics";

const isProd = process.env.NODE_ENV === "production";

Vue.config.productionTip = false;
Vue.use(VueAnalytics, {
  id: "UA-176434466-1",
  debug: {
    enable: !isProd,
    sendHitTask: isProd
  },
  router
});

new Vue({
  router,
  store,
  vuetify,
  editor,
  render: h => h(App)
}).$mount("#app");
