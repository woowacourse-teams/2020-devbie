import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import { router } from "./router";
import { store } from "./store";
import { editor } from "./utils/editor";
import VueAnalytics from "vue-analytics";
import VueMq from "vue-mq";

const isProd = process.env.NODE_ENV === "production";

Vue.use(VueAnalytics, {
  id: "UA-176434466-1",
  debug: {
    enable: !isProd,
    sendHitTask: isProd
  },
  router
});

Vue.use(VueMq, {
  breakpoints: {
    mobile: 450,
    tablet: 900,
    laptop: 1250,
    desktop: Infinity
  }
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  editor,
  render: h => h(App)
}).$mount("#app");
