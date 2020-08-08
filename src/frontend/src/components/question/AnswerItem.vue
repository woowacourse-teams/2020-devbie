<template>
  <div class="answer-item-box">
    <div class="author-name">작성자: {{ answer.userId }}</div>
    <div class="answer-temp">
      <div class="answer-content">
        <p class="answer-content-value" v-if="!this.updateEditFlag">
          {{ answer.content }}
        </p>
        <v-textarea
          class="update-form"
          outlined
          v-else
          v-model="updateContent"
        ></v-textarea>
      </div>
      <div :class="{ 'vertical-center': !author }" class="answer-infos">
        <div class="recommendations">
          <p class="infos">
            <i
              :class="{
                'recommendation-clicked': isUserRecommendation('RECOMMENDED')
              }"
              class="far fa-thumbs-up recommendation"
              @click="onAnswerRecommendation('NON_RECOMMENDED', 'RECOMMENDED')"
            ></i>
            {{ answerRecommendation && answerRecommendation.recommendedCount }}
          </p>
          <p class="infos">
            <i
              :class="{
                'recommendation-clicked': isUserRecommendation(
                  'NON_RECOMMENDED'
                )
              }"
              class="far fa-thumbs-down recommendation"
              @click="onAnswerRecommendation('RECOMMENDED', 'NON_RECOMMENDED')"
            ></i>
            {{
              answerRecommendation && answerRecommendation.nonRecommendedCount
            }}
          </p>
        </div>
        <div v-if="author">
          <v-btn class="update-btn" v-if="updateEditFlag" @click="update">
            수정 확인
          </v-btn>
          <v-btn class="update-btn" v-else @click="updateBtnHandler"
            >수정
          </v-btn>
          <v-btn @click="deleteBtnHandler">삭제</v-btn>
        </div>
      </div>
    </div>
    <v-snackbar v-model="snackbar" :multi-line="true" top>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="snackbar = false">
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ["answer"],
  data: function() {
    return {
      loginUser: {},
      author: false,
      userRecommended: "",
      updateEditFlag: false,
      updateContent: this.answer.content,
      snackbar: false,
      snackbarText: ""
    };
  },
  computed: {
    ...mapGetters([
      "fetchedLoginUser",
      "fetchedAnswerRecommendation",
      "fetchedMyAnswerRecommendation"
    ]),
    answerRecommendation() {
      return this.fetchedAnswerRecommendation(this.answer.id);
    },
    myAnswerRecommendation() {
      return this.fetchedMyAnswerRecommendation(this.answer.id);
    }
  },
  methods: {
    isAuthor() {
      return (this.author = this.answer.userId === this.loginUser.id);
    },
    async deleteBtnHandler() {
      await this.$store.dispatch("DELETE_ANSWER", this.answer.id);
    },
    updateBtnHandler() {
      this.updateEditFlag = !this.updateEditFlag;
    },
    async update() {
      const answerId = this.answer.id;
      const updateContent = this.updateContent;
      await this.$store.dispatch("UPDATE_ANSWER", {
        answerId,
        updateContent
      });
      this.updateEditFlag = !this.updateEditFlag;
    },
    async onAnswerRecommendation(priorType, newType) {
      if (!this.loginUser.id) {
        console.log("you should login");
        this.snackbarText = "로그인 후 추천/비추천 할 수 있습니다. ";
        this.snackbar = true;
        return;
      }
      const answerId = this.answer.id;
      if (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      ) {
        try {
          await this.$store.dispatch("ON_ANSWER_RECOMMENDATION", {
            answerId,
            recommendationType: newType
          });
          this.userRecommended = newType;
        } catch (error) {
          console.log(error);
          if (error.response.status === 401) {
            this.snackbarText = "로그인 후 추천/비추천 할 수 있습니다. ";
            this.snackbar = true;
          }
        }
      } else {
        try {
          await this.$store.dispatch("DELETE_ANSWER_RECOMMENDATION", answerId);
          this.userRecommended = "NOT_EXIST";
        } catch (error) {
          console.log(error);
          if (error.response.status === 401) {
            this.snackbarText = "로그인 후 추천/비추천 할 수 있습니다. ";
            this.snackbar = true;
          }
        }
      }
      await this.$store.dispatch("FETCH_ANSWER_RECOMMENDATION", answerId);
    },
    async fetchMyAnswerRecommendation(answerId, userId) {
      await this.$store.dispatch("FETCH_MY_ANSWER_RECOMMENDATION", {
        answerId,
        userId
      });
      this.userRecommended = this.myAnswerRecommendation.recommendationType;
    },
    isUserRecommendation(recommendationType) {
      return (
        this.myAnswerRecommendation &&
        this.userRecommended === recommendationType
      );
    }
  },
  watch: {
    fetchedLoginUser: async function() {
      this.loginUser = this.fetchedLoginUser;
      if (!this.loginUser.id) {
        this.userRecommended = "NOT_EXIST";
        this.isAuthor();
        return;
      }
      await this.fetchMyAnswerRecommendation(this.answer.id, this.loginUser.id);
      this.isAuthor();
    }
  },
  async created() {
    this.loginUser = this.fetchedLoginUser;
    await this.$store.dispatch("FETCH_ANSWER_RECOMMENDATION", this.answer.id);
    if (this.loginUser.id) {
      await this.fetchMyAnswerRecommendation(this.answer.id, this.loginUser.id);
    }
    await this.isAuthor();
  }
};
</script>

<style scoped>
.author-name {
  margin-top: 15px;
  color: #7ec699;
}

.answer-item-box {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
}

.answer-content {
  padding: 30px 20px 30px 50px;
}

.answer-temp {
  display: flex;
  justify-content: space-between;
  border-bottom: solid #e8e8e8 1px;
}

.recommendations {
  display: flex;
  justify-content: center;
  align-items: center;
}

.answer-infos {
  min-width: 135px;
}

.recommendations .infos {
  margin: 0 10px 10px 0;
}

.recommendation:hover {
  cursor: pointer;
}

.recommendation-clicked {
  color: #7ec699;
}

.answer-content {
  min-width: 80%;
}

.update-btn {
  margin-right: 7px;
}

.answer-content-value {
  max-width: 1100px;
}
</style>
