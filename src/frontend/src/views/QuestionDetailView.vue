<template>
  <div class="detail">
    <question-detail id="question-detail"></question-detail>
  </div>
</template>

<script>
import QuestionDetail from "../components/QuestionDetail";

export default {
  components: {
    QuestionDetail
  }
};
</script>

<style scoped>
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
