<template>
  <div id="app">
    {{ message }}
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      message: ""
    };
  },
  async mounted() {
    //http://localhost:8080/api/hello
    const { data } = await axios.get("/api/hello");
    this.message = data;
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
