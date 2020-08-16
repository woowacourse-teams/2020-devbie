<template>
  <div>
    <form-title>
      <span>질문 수정</span>
    </form-title>
    <question-create :editingFlag="true"></question-create>
  </div>
</template>

<script>
import QuestionCreate from "../../components/question/QuestionForm";
import FormTitle from "../../components/question/FormTitle";

export default {
  components: {
    FormTitle,
    QuestionCreate
  }
};
</script>

<style scoped></style>
