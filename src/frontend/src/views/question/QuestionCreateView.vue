<template>
  <div>
    <form-title>
      <span>질문 생성</span>
    </form-title>
    <question-form :editingFlag="false"></question-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import QuestionForm from "../../components/question/QuestionForm";
import FormTitle from "../../components/question/FormTitle";
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
        router.push("/questions");
      }
    }
  }
};
</script>

<style scoped></style>
