<template>
  <div>
    <a @click="$router.push(`/notices/create`)">글쓰기</a>
  </div>
</template>

<script>
export default {
  async created() {
    await this.isAdmin();
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

<style scoped></style>
