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
            <i
              :class="{
                'recommendation-clicked': userRecommended === 'RECOMMENDED'
              }"
              class="far fa-thumbs-up"
              @click="
                onQuestionRecommendation('NON_RECOMMENDED', 'RECOMMENDED')
              "
            ></i>
            {{ fetchedQuestionRecommendation.recommendedCount }}
          </p>
          <p class="infos">
            <i
              :class="{
                'recommendation-clicked': userRecommended === 'NON_RECOMMENDED'
              }"
              class="far fa-thumbs-down recommendation"
              @click="
                onQuestionRecommendation('RECOMMENDED', 'NON_RECOMMENDED')
              "
            ></i>
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
  data() {
    return {
      questionId: this.$route.params.id,
      userRecommended: ""
    };
  },
  computed: {
    ...mapGetters([
      "fetchedLoginUser",
      "fetchedQuestion",
      "fetchedQuestionRecommendation",
      "fetchedMyQuestionRecommendation"
    ])
  },
  methods: {
    async onQuestionRecommendation(priorType, newType) {
      const questionId = this.questionId;
      if (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      ) {
        const request = {
          recommendationType: newType
        };
        await this.$store.dispatch("ON_QUESTION_RECOMMENDATION", {
          questionId,
          request
        });
        this.userRecommended = newType;
      } else {
        await this.$store.dispatch(
          "DELETE_QUESTION_RECOMMENDATION",
          questionId
        );
        this.userRecommended = "NOT_EXIST";
      }
      await this.$store.dispatch(
        "FETCH_QUESTION_RECOMMENDATION",
        this.questionId
      );
    }
  },
  async created() {
    const questionId = this.questionId;
    const userId = this.fetchedLoginUser.id;

    await this.$store.dispatch("FETCH_QUESTION", this.questionId);
    await this.$store.dispatch("FETCH_MY_QUESTION_RECOMMENDATION", {
      questionId,
      userId
    });
    this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
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
.recommendation-clicked {
  color: #7ec699;
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
