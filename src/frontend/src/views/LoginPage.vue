<template>
  <div class="flex-box">
    <v-progress-circular
      v-if="isNotCompleted"
      :size="200"
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
    const response = await axios.post("/api/auth/login?code=" + code);
    const { token } = await response.data;

    localStorage.setItem("devbieToken", token);
    this.isNotCompleted = false;
    window.location.href = "/";
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
