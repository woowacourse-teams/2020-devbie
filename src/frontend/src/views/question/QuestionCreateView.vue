<template>
  <div>
    <form-title>
      <span>질문 수정</span>
    </form-title>
    <question-create :editingFlag="false"></question-create>
  </div>
</template>

<script>
import QuestionCreate from "../../components/question/QuestionForm";
import FormTitle from "../../components/question/FormTItle";

export default {
  components: {
    FormTitle,
    QuestionCreate
  }
};
</script>

<style scoped></style>
