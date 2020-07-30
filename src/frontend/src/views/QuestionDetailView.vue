<template>
  <div class="detail">
    <div class="left-menu"></div>
    <div class="question-box">
      <question-detail id="question-detail"></question-detail>
      <answer-list></answer-list>
    </div>
  </div>
</template>

<script>
import QuestionDetail from "../components/QuestionDetail";
import AnswerList from "../components/AnswerList";

export default {
  components: {
    QuestionDetail,
    AnswerList
  }
};
</script>

<style scoped>
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
}
.question-box {
  display: flex;
  flex-direction: column;
  flex-grow: 8;
}
</style>
