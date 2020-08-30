<template>
  <div class="container" :class="$mq">
    <v-btn
      rounded
      small
      class="recommendation-btn"
      :class="{
        clicked: isUserAction('RECOMMENDED'),
        $mq
      }"
      @click="onRecommendation('NON_RECOMMENDED', 'RECOMMENDED')"
    >
      <i class="far fa-thumbs-up recommendation"></i>
      <span class="recommendation">{{ targetObject.recommendedCount }}</span>
    </v-btn>
    <v-btn
      rounded
      small
      class="recommendation-btn"
      :class="{
        clicked: isUserAction('NON_RECOMMENDED'),
        $mq
      }"
      @click="onRecommendation('RECOMMENDED', 'NON_RECOMMENDED')"
    >
      <i class="far fa-thumbs-down recommendation"></i>
      <span class="recommendation">{{ targetObject.nonRecommendedCount }}</span>
    </v-btn>
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
      "isLoggedIn",
      "fetchedLoginUser",
      "fetchedMyQuestionRecommendation",
      "fetchedMyAnswerRecommendation"
    ]),

    myAnswerRecommendation() {
      return this.fetchedMyAnswerRecommendation(this.targetObject.id);
    }
  },

  watch: {
    fetchedMyQuestionRecommendation() {
      if (this.isQuestion) {
        this.$store.dispatch(
          "FETCH_QUESTION_WITHOUT_VISITS",
          this.targetObject.id
        );
        this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
      }
    },

    isLoggedIn() {
      this.initRecommendationState();
    }
  },

  created() {
    if (this.isLoggedIn) {
      this.initRecommendationState();
    }
  },

  methods: {
    async initRecommendationState() {
      await this.$store.dispatch("FETCH_LOGIN_USER");
      await this.$store.dispatch("FETCH_MY_RECOMMENDATION", {
        object: this.isQuestion ? "question" : "answer",
        objectId: this.targetObject.id,
        userId: this.fetchedLoginUser.id
      });

      if (this.isQuestion) {
        this.userRecommended = this.fetchedMyQuestionRecommendation.recommendationType;
      } else {
        this.userRecommended =
          this.myAnswerRecommendation &&
          this.myAnswerRecommendation.recommendationType;
      }
    },

    async onRecommendation(priorType, newType) {
      if (!this.isLoggedIn) {
        console.log("you should login");
        await this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "로그인이 필요합니다."
        );
        return;
      }
      if (this.isCreateOrUpdateRecommendation(priorType)) {
        await this.$store.dispatch("CREATE_RECOMMENDATION", {
          object: this.isQuestion ? "question" : "answer",
          objectId: this.targetObject.id,
          recommendationType: newType
        });
      } else {
        await this.$store.dispatch("DELETE_RECOMMENDATION", {
          object: this.isQuestion ? "question" : "answer",
          objectId: this.targetObject.id
        });
      }

      if (!this.isQuestion) {
        await this.$store.dispatch("FETCH_ANSWERS", this.$route.params.id);
        this.userRecommended = this.myAnswerRecommendation.recommendationType;
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
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-around;
  padding: 12px 9px;
}

.container.mobile {
  padding: 0 10px 0 0;
}

.recommendation {
  font-size: 17px;
  padding: 3px;
}

.recommendation-btn.mobile {
  margin: 0 4px !important;
  padding: 0 !important;
  width: 40px !important;
}

.recommendation-btn:hover {
  cursor: pointer;
}

.fa-thumbs-up {
  color: #4ea1d3;
}

.fa-thumbs-down {
  color: #e85a71;
}

.clicked {
  background-color: #f6ea8c !important;
}
</style>
