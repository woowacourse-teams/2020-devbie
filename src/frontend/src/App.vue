<template>
  <v-app id="app">
    <navigation-bar @logout="logout"></navigation-bar>
    <transition name="page">
      <router-view :key="$route.fullPath" class="content"></router-view>
    </transition>
    <footer-bar></footer-bar>
  </v-app>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";

export default {
  async beforeCreate() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        await this.$store.dispatch("FETCH_LOGIN_USER");
      } catch (error) {
        localStorage.removeItem("devbieToken");
        this.$store.commit("DELETE_LOGIN_USER");
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("devbieToken");
      this.$store.commit("DELETE_LOGIN_USER");
    }
  },
  components: {
    NavigationBar,
    FooterBar
  }
};
</script>

<style>
#app {
  font-family: "Noto Sans KR", "Noto Sans JP", sans-serif;
  color: #35495e;
}

.content {
  min-height: calc(100vh - 220px);
}

a {
  text-decoration: none;
}

.page-enter-active,
.page-leave-active {
  transition: opacity 0.5s;
}

.page-enter,
.page-leave-to {
  opacity: 0;
}
</style>
