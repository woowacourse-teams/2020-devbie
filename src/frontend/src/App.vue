<template>
  <v-app id="app">
    <navigation-bar @logout="logout"></navigation-bar>
    <transition name="fade" mode="out-in">
      <router-view :key="$route.fullPath" class="content"></router-view>
    </transition>
    <snack-bar></snack-bar>
    <chat-drawer></chat-drawer>
    <footer-bar></footer-bar>
  </v-app>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";
import SnackBar from "./components/SnackBar";
import ChatDrawer from "./components/chat/ChatDrawer";

export default {
  async beforeCreate() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        await this.$store.dispatch("FETCH_LOGIN_USER");
      } catch (error) {
        console.log(error.response.data.message);
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인 실패하였습니다.");
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
    ChatDrawer,
    NavigationBar,
    FooterBar,
    SnackBar
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

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
