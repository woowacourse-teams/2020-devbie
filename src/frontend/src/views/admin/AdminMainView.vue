<template>
  <div class="inner">
    <div class="notice-upload-btn">
      <v-btn x-large color="teal" dark @click="$router.push(`/notices/create`)"
        >글쓰기</v-btn
      >
    </div>
  </div>
</template>

<script>
export default {
  created() {
    this.isAdmin();
  },

  methods: {
    async isAdmin() {
      const fetchedLoginUser = await this.$store.getters.fetchedLoginUser;
      if (fetchedLoginUser === null || fetchedLoginUser.roleType !== "ADMIN") {
        await this.$router.push("/");
      }
    }
  }
};
</script>

<style scoped>
.inner {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
