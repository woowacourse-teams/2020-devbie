<template>
  <div class="update-form">
    <h1 id="title">질문 수정 페이지</h1>
    <update-form></update-form>
  </div>
</template>

<script>
import UpdateForm from "../components/UpdateForm";

export default {
  components: {
    UpdateForm
  }
};
</script>

<style scoped>
#title {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
