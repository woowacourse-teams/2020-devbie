<template>
  <v-app id="app">
    <navigation-bar :isLoggedIn="isLoggedIn" @logout="logout"></navigation-bar>
    <transition name="page">
      <router-view :key="$route.fullPath" class="content"></router-view>
    </transition>
    <v-snackbar v-model="snackbar" :multi-line="true" top>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="snackbar = false">
          닫기
        </v-btn>
      </template>
    </v-snackbar>
    <footer-bar></footer-bar>
  </v-app>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";

export default {
  data() {
    return {
      isLoggedIn: false,
      snackbar: false,
      snackbarText: ""
    };
  },
  async created() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        await this.$store.dispatch("FETCH_LOGIN_USER");
        this.isLoggedIn = true;
      } catch (error) {
        this.snackbar = true;
        this.snackbarText = error.response.data.message;
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
    FooterBar
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
