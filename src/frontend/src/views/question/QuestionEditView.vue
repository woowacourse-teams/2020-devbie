<template>
  <div class="edit-form">
    <h2 class="title">질문 수정 페이지</h2>
    <edit-form id="edit-form"></edit-form>
  </div>
</template>

<script>
import EditForm from "../../components/question/EditForm";

export default {
  components: {
    EditForm
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
