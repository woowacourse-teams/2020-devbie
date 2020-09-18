<template>
  <div class="detail" :class="$mq">
    <question-detail-menu :isAuthor="isAuthor"></question-detail-menu>
    <div class="question-box" :class="$mq">
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
}

.detail.mobile {
  flex-direction: column;
}

.detail-items {
  max-width: 90%;
}

.question-box {
  margin: 27px auto;
  border-left: solid 1px #e8e8e8;
  display: flex;
  flex-direction: column;
  align-items: center;
  flex-grow: 8;
}

.question-box.mobile {
  margin: 20px 0 0 0;
  border-left: none;
}
</style>
