<template>
  <div id="app">
    <navigation-bar></navigation-bar>
    <footer-bar></footer-bar>
  </div>
</template>

<script>
import axios from "axios";
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";

export default {
  data() {
    return {
      message: ""
    };
  },
  components: {
    NavigationBar,
    FooterBar
  },
  async mounted() {
    const { data } = await axios.get("/api/hello");
    this.message = data;
  }
};
</script>

<style>
body {
  font-family: "Do Hyeon", sans-serif;
}
</style>
