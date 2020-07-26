<template>
  <div class="question-detail">
    <p>번호 : {{ fetchedQuestion.questionId }}</p>
    <p>작성자 : {{ fetchedQuestion.userId }}</p>
    <p>조회수 : {{ fetchedQuestion.visits }}</p>
    <h2>제목 : {{ fetchedQuestion.title }}</h2>
    <p>내용 : {{ fetchedQuestion.content }}</p>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters(["fetchedQuestion"])
  },
  created() {
    const questionId = this.$route.params.id;
    console.log(questionId);
    this.$store.dispatch("FETCH_QUESTION", questionId);
  }
};
</script>

<style scoped>
.question-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
