<template>
  <div>
    <h2 class="title">질문 작성</h2>
    <create-form id="create-form"></create-form>
  </div>
</template>

<script>
import CreateForm from "../../components/question/CreateForm";

export default {
  components: {
    CreateForm
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
