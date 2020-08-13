<template>
  <div>
    <h2 class="title">질문 작성</h2>
    <question-create id="create-form"></question-create>
  </div>
</template>

<script>
import QuestionCreate from "../../components/question/QuestionCreate";

export default {
  components: {
    QuestionCreate
  }
};
</script>

<style scoped>
.title {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

#create-form {
  margin-top: 15px;
}
</style>
