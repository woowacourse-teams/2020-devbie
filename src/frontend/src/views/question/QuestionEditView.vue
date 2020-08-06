<template>
  <div class="edit-form">
    <h2 class="title">질문 수정 페이지</h2>
    <question-edit id="edit-form"></question-edit>
  </div>
</template>

<script>
import QuestionEdit from "../../components/question/QuestionEdit";

export default {
  components: {
    QuestionEdit
  }
};
</script>

<style scoped>
.title {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

#edit-form {
  margin-top: 15px;
}
</style>
