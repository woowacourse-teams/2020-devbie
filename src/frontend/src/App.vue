<template>
  <div id="app">
    <navigation-bar></navigation-bar>
    {{ email }}
    <router-view></router-view>
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
      email: ""
    };
  },
  async mounted() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        const response = await axios.get("/api/user", {
          headers: {
            Authorization: `bearer ${token}`
          }
        });
        const { email } = await response.data;
        this.email = email;
      } catch (error) {
        localStorage.removeItem("devbieToken");
      }
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
</style>
