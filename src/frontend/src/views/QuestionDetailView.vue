<template>
  <div class="detail">
    <div class="left-menu"></div>
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
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
}
#question-detail {
  flex-grow: 8;
}
</style>
