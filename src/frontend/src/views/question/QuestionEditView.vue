<template>
  <div>
    <form-title>
      <span>질문 수정</span>
    </form-title>
    <question-form :editingFlag="true"></question-form>
  </div>
</template>

<script>
import QuestionForm from "../../components/question/QuestionForm";
import FormTitle from "../../components/question/FormTitle";
import { mapGetters } from "vuex";
import router from "../../router";

export default {
  components: {
    FormTitle,
    QuestionForm
  },

  computed: {
    ...mapGetters(["isLoggedIn"])
  },

  created() {
    this.checkLoggedIn();
  },

  methods: {
    checkLoggedIn() {
      if (!this.isLoggedIn) {
        router.push("/");
      }
    }
  }
};
</script>

<style scoped></style>
