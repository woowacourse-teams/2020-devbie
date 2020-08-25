<template>
  <div class="detail">
    <question-detail-menu :isAuthor="isAuthor"></question-detail-menu>
    <div class="question-box">
      <question-detail
        :loginUser="fetchedLoginUser"
        class="detail-items"
      ></question-detail>
      <answer-list
        class="detail-items"
        :loginUser="fetchedLoginUser"
      ></answer-list>
      <answer-create class="detail-items"></answer-create>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import QuestionDetailMenu from "../../components/question/QuestionDetailMenu";
import QuestionDetail from "../../components/question/QuestionDetail";
import AnswerList from "../../components/question/AnswerList";
import AnswerCreate from "../../components/question/AnswerCreate";

export default {
  components: {
    QuestionDetailMenu,
    QuestionDetail,
    AnswerList,
    AnswerCreate
  },

  computed: {
    ...mapGetters(["fetchedQuestion", "fetchedLoginUser", "isLoggedIn"]),

    isAuthor() {
      return (
        this.isLoggedIn &&
        this.fetchedQuestion.userId === this.fetchedLoginUser.id
      );
    }
  },

  async created() {
    await this.$store.dispatch("FETCH_QUESTION", this.$route.params.id);
  }
};
</script>

<style scoped>
a {
  text-decoration: none;
}

.detail {
  display: flex;
  justify-content: center;
  margin: 20px 30px 0 0;
}

.detail-items {
  max-width: 90%;
}

.question-box {
  display: flex;
  flex-direction: column;
  flex-grow: 8;
}
</style>
