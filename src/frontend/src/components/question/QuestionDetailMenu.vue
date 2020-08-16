<template>
  <div class="left-menu">
    <v-btn
      color="#DAEBEA"
      @click="$router.push(`/questions`)"
      class="menu-btn button"
      >돌아가기</v-btn
    >
    <div class="author-btn" v-if="isAuthor">
      <v-btn
        color="#DAEBEA"
        @click="$router.push(`/question/edit/${$route.params.id}`)"
        class="menu-btn button"
        >수정하기</v-btn
      >
      <v-btn
        @click="onDeleteQuestion"
        color="#DAEBEA"
        class="menu-btn button primary"
        >삭제하기
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isAuthor"],
  methods: {
    async onDeleteQuestion() {
      if (confirm("정말 삭제하시겠습니까?")) {
        const questionId = this.$route.params.id;
        await this.$store.dispatch("DELETE_QUESTION", questionId);
        await this.$router.push(`/questions`);
      }
    }
  }
};
</script>

<style scoped>
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 220px;
}

.menu-btn {
  margin-top: 30px;
}

.author-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
