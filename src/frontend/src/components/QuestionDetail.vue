<template>
  <div class="question-detail">
    <div class="inner">
      <div class="question-header">
        <div class="question-title">
          <h1>
            Q{{ fetchedQuestion.questionId }}. {{ fetchedQuestion.title }}
          </h1>
        </div>
        <div class="question-info">
          <p class="infos">
            <i class="fas fa-user-edit"></i>
            {{ fetchedQuestion.userId }}
          </p>
          <p class="infos">
            <i class="fas fa-eye"></i>
            {{ fetchedQuestion.visits }}
          </p>
          <p class="infos">
            <i class="far fa-thumbs-up"></i>
            {{ fetchedQuestionRecommendation.recommendedCount }}
          </p>
          <p class="infos">
            <i class="far fa-thumbs-down"></i>
            {{ fetchedQuestionRecommendation.nonRecommendedCount }}
          </p>
        </div>
      </div>
      <div class="question-content">
        <p>{{ fetchedQuestion.content }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedQuestion"]),
    ...mapGetters(["fetchedQuestionRecommendation"])
  },
  async created() {
    const questionId = this.$route.params.id;
    await this.$store.dispatch("FETCH_QUESTION", questionId);
    await this.$store.dispatch("FETCH_QUESTION_RECOMMENDATION", questionId);
    await this.$emit("fetchUserId", this.fetchedQuestion.userId);
  }
};
</script>

<style scoped>
.question-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 20px;
}
.question-header {
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
}
.question-info {
  display: flex;
  justify-content: flex-end;
}
.question-info .infos {
  font-size: 16px;
  margin-right: 15px;
}
.question-info .infos:last-child {
  margin-right: 5px;
}
.question-content {
  padding: 30px 50px;
}
.inner {
  width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
}
</style>
