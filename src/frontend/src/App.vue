<template>
  <v-app id="app">
    <navigation-bar :isLoggedIn="isLoggedIn" @logout="logout"></navigation-bar>
    <transition name="page">
      <router-view :key="$route.fullPath" class="content"></router-view>
    </transition>
    <snack-bar></snack-bar>
    <footer-bar></footer-bar>
  </v-app>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";
import SnackBar from "./components/SnackBar";

export default {
  data() {
    return {
      isLoggedIn: false
    };
  },
  async created() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        await this.$store.dispatch("FETCH_LOGIN_USER");
        this.isLoggedIn = true;
      } catch (error) {
        console.log(error.response.data.message);
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인 실패하였습니다.");
        localStorage.removeItem("devbieToken");
        this.isLoggedIn = false;
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("devbieToken");
      this.$store.commit("DELETE_LOGIN_USER");
      this.isLoggedIn = false;
    }
  },
  components: {
    NavigationBar,
    FooterBar,
    SnackBar
  }
};
</script>

<style>
#app {
  font-family: "Do Hyeon", sans-serif;
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
