import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import { router } from "./router";
import { store } from "./store";
import { editor } from "./utils/editor";

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  editor,
  render: h => h(App)
}).$mount("#app");
