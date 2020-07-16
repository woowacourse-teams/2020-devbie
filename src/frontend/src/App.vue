<template>
  <div id="app">
    <navigation-bar></navigation-bar>
    <router-view></router-view>
    <footer-bar></footer-bar>
  </div>
</template>

<script>
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";

export default {
  data() {
    return {};
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
