<template>
  <div id="app">
    <navigation-bar :isLoggedIn="isLoggedIn" @logout="logout"></navigation-bar>
    <transition name="page">
      <router-view></router-view>
    </transition>
    <footer-bar></footer-bar>
  </div>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";
import axios from "axios";

export default {
  data() {
    return {
      email: "",
      isLoggedIn: false
    };
  },
  async mounted() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        const response = await axios.get("/api/users", {
          headers: {
            Authorization: `bearer ${token}`
          }
        });
        const { email } = await response.data;
        this.email = email;
        this.isLoggedIn = true;
      } catch (error) {
        localStorage.removeItem("devbieToken");
        this.email = "";
        this.isLoggedIn = false;
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("devbieToken");
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
body {
  font-family: "Do Hyeon", sans-serif;
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
