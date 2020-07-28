<template>
  <div class="detail">
    <div class="left-menu">
      <router-link :to="`/questions`"
        ><v-btn color="#DAEBEA" class="menu-btn">돌아가기</v-btn></router-link
      >
      <router-link :to="`/edit-question/${this.$route.params.id}`"
        ><v-btn color="#DAEBEA" class="menu-btn">수정하기</v-btn></router-link
      >
      <v-btn @click="onDeleteQuestion" color="#E8E8E8" class="menu-btn"
        >삭제하기</v-btn
      >
    </div>
    <question-detail id="question-detail"></question-detail>
  </div>
</template>

<script>
import QuestionDetail from "../components/QuestionDetail";

export default {
  components: {
    QuestionDetail
  },
  methods: {
    async onDeleteQuestion() {
      if (confirm("정말 삭제하시겠습니까?")) {
        const questionId = this.$route.params.id;
        await this.$store.dispatch("DELETE_QUESTION", questionId);
        window.location.href = `/questions`;
      }
    }
  }
};
</script>

<style scoped>
a {
  text-decoration: none;
}
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.menu-btn {
  margin-top: 30px;
}
#question-detail {
  flex-grow: 8;
}
</style>
