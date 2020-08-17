<template>
  <div>
    <p class="infos">
      <i
        :class="{
          'recommendation-clicked': isUserAction('RECOMMENDED')
        }"
        class="far fa-thumbs-up recommendation"
        @click="onRecommendation('NON_RECOMMENDED', 'RECOMMENDED')"
      ></i>
      {{ targetObject.recommendedCount }}
    </p>
    <p class="infos">
      <i
        :class="{
          'recommendation-clicked': isUserAction('NON_RECOMMENDED')
        }"
        class="far fa-thumbs-down recommendation"
        @click="onRecommendation('RECOMMENDED', 'NON_RECOMMENDED')"
      ></i>
      {{ targetObject.nonRecommendedCount }}
    </p>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ["targetObject", "isQuestion"],
  data() {
    return {
      userRecommended: ""
    };
  },
  computed: {
    ...mapGetters([
      "fetchedLoginUser",
      "fetchedMyQuestionRecommendation",
      "fetchedMyAnswerRecommendation"
    ]),
    myAnswerRecommendation() {
      return this.fetchedMyAnswerRecommendation(this.targetObject.id);
    }
  },
  methods: {
    async onRecommendation(priorType, newType) {
      if (!this.fetchedLoginUser) {
        console.log("you should login");
        return;
      }
      if (this.isCreateOrUpdateRecommendation(priorType)) {
        await this.$store.dispatch("CREATE_RECOMMENDATION", {
          object: this.isQuestion ? "question" : "answer",
          objectId: this.targetObject.id,
          recommendationType: newType
        });
        this.userRecommended = newType;
      } else {
        await this.$store.dispatch(
          "DELETE_RECOMMENDATION",
          this.targetObject.id
        );
        this.userRecommended = "NOT_EXIST";
      }

      if (!this.isQuestion) {
        await this.$store.dispatch("FETCH_ANSWERS", this.$route.params.id);
      }
    },
    isCreateOrUpdateRecommendation(priorType) {
      return (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      );
    },
    isUserAction(recommendationType) {
      return this.userRecommended === recommendationType;
    },
    initUserRecommended() {
      if (this.isQuestion) {
        this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
      } else {
        this.userRecommended = this.myAnswerRecommendation.recommendationType;
      }
    }
  },
  watch: {
    fetchedMyQuestionRecommendation() {
      if (this.isQuestion) {
        this.$store.dispatch(
          "UPDATE_QUESTION_RECOMMENDATION_COUNT",
          this.targetObject.id
        );
      }
    }
  },
  async created() {
    await this.$store.dispatch("FETCH_LOGIN_USER");
    await this.$store.dispatch("FETCH_MY_RECOMMENDATION", {
      object: this.isQuestion ? "question" : "answer",
      objectId: this.targetObject.id,
      userId: this.fetchedLoginUser.id
    });
    this.initUserRecommended();
  }
};
</script>

<style scoped>
.recommendation:hover {
  cursor: pointer;
}

.recommendation-clicked {
  color: #7ec699;
}
</style>
