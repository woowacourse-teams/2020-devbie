<template>
  <div class="detail">
    <question-detail-menu :isAuthor="isAuthor"></question-detail-menu>
    <div class="question-box">
      <question-detail
        :questionId="questionId"
        id="question-detail"
      ></question-detail>
      <answer-list></answer-list>
      <answer-create></answer-create>
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
  data() {
    return {
      questionId: this.$route.params.id
    };
  },
  computed: {
    ...mapGetters(["fetchedQuestion", "fetchedLoginUser"]),
    isAuthor() {
      return this.fetchedQuestion.userId === this.fetchedLoginUser.id;
    }
  },
  async created() {
    await this.$store.dispatch("FETCH_QUESTION", this.questionId);
  },
  components: {
    QuestionDetailMenu,
    QuestionDetail,
    AnswerList,
    AnswerCreate
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
  margin: 20px 0 auto;
  max-width: 95%;
}

#question-detail {
  margin-bottom: 60px;
}

.question-box {
  display: flex;
  flex-direction: column;
  flex-grow: 8;
}
</style>
