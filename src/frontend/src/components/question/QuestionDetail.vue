<template>
  <div class="question-detail">
    <div class="inner">
      <div class="question-header">
        <div class="question-title">
          <h1>
            Q{{ fetchedQuestion.questionId }}. {{ fetchedQuestion.title }}
          </h1>
        </div>
        <div class="question-header-bottom">
          <div class="hashtags">
            <span
              v-for="hashtag in fetchedQuestion.hashtags"
              v-bind:key="hashtag.id"
              class="hashtag"
              @click="$router.push(`/questions?hashtag=${hashtag.tagName}`)"
              >#{{ hashtag.tagName }}
            </span>
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
                  'recommendation-clicked': isUserRecommendation('RECOMMENDED')
                }"
                class="far fa-thumbs-up recommendation"
                @click="
                  onQuestionRecommendation('NON_RECOMMENDED', 'RECOMMENDED')
                "
              ></i>
              {{ fetchedQuestion.recommendedCount }}
            </p>
            <p class="infos">
              <i
                :class="{
                  'recommendation-clicked': isUserRecommendation(
                    'NON_RECOMMENDED'
                  )
                }"
                class="far fa-thumbs-down recommendation"
                @click="
                  onQuestionRecommendation('RECOMMENDED', 'NON_RECOMMENDED')
                "
              ></i>
              {{ fetchedQuestion.nonRecommendedCount }}
            </p>
          </div>
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
      loginUser: {},
      questionId: this.$route.params.id,
      userRecommended: ""
    };
  },
  computed: {
    ...mapGetters([
      "fetchedLoginUser",
      "fetchedQuestion",
      "fetchedMyQuestionRecommendation"
    ])
  },
  methods: {
    async onQuestionRecommendation(priorType, newType) {
      if (!this.loginUser.id) {
        console.log("you should login");
        return;
      }
      if (this.isCreateOrUpdateRecommendation(priorType)) {
        await this.$store.dispatch("ON_QUESTION_RECOMMENDATION", {
          questionId: this.questionId,
          recommendationType: newType
        });
      } else {
        await this.$store.dispatch(
          "DELETE_QUESTION_RECOMMENDATION",
          this.questionId
        );
      }
    },
    async fetchMyQuestionRecommendation(questionId, userId) {
      await this.$store.dispatch("FETCH_MY_QUESTION_RECOMMENDATION", {
        questionId,
        userId
      });
      this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
    },
    isCreateOrUpdateRecommendation(priorType) {
      return (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      );
    },
    isUserRecommendation(recommendationType) {
      return (
        this.fetchedMyQuestionRecommendation &&
        this.userRecommended === recommendationType
      );
    }
  },
  watch: {
    fetchedLoginUser() {
      this.loginUser = this.fetchedLoginUser;
      if (!this.fetchedLoginUser.id) {
        this.userRecommended = "NOT_EXIST";
        return;
      }
      this.fetchMyQuestionRecommendation(
        this.questionId,
        this.fetchedLoginUser.id
      );
    },
    fetchedMyQuestionRecommendation: async function() {
      this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
      await this.$store.dispatch(
        "UPDATE_QUESTION_RECOMMENDATION_COUNT",
        this.questionId
      );
    }
  },
  async created() {
    this.loginUser = this.fetchedLoginUser;
    await this.$store.dispatch("FETCH_QUESTION", this.questionId);
    if (this.fetchedLoginUser.id) {
      await this.fetchMyQuestionRecommendation(
        this.questionId,
        this.fetchedLoginUser.id
      );
    }
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

.question-title {
  margin-bottom: 12px;
}

.question-header {
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
}

.question-header-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.hashtag {
  margin: 0 9px;
  color: #0086b3;
}

.hashtag:hover {
  cursor: pointer;
  font-weight: bold;
  color: #445588;
  text-decoration: underline;
}

.question-info {
  min-width: 180px;
  display: flex;
  justify-content: flex-end;
}

.question-info .infos {
  font-size: 16px;
  margin-right: 15px;
  margin-bottom: 0;
}

.recommendation:hover {
  cursor: pointer;
}

.recommendation-clicked {
  color: #7ec699;
}

.question-info .infos:last-child {
  margin-right: 5px;
}

.question-content {
  padding: 30px 50px;
  margin-bottom: 50px;
}

.inner {
  width: 95%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
  height: 400px;
}
</style>
