<template>
  <div class="flex-box">
    <v-progress-circular
      v-if="isNotCompleted"
      :size="150"
      indeterminate
      class="progress-circular"
    ></v-progress-circular>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      isNotCompleted: true
    };
  },

  async beforeCreate() {
    const code = this.$route.query.code;
    const redirectUri = this.$route.query["redirect-uri"];

    const response = await axios.post("/api/auth/login?code=" + code);
    const { token } = await response.data;

    await localStorage.setItem("devbieToken", token);
    this.isNotCompleted = false;

    let nextUrl = "/";
    if (redirectUri !== undefined) {
      nextUrl = redirectUri;
    }

    window.location.href = nextUrl;
  }
};
</script>

<style>
.flex-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.progress-circular {
  color: rgb(159, 208, 212);
}
</style>
