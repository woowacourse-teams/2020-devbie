<template>
  <div class="answer-item-box">
    <div class="author-name">작성자: {{ answer.userId }}</div>
    <div class="answer-temp">
      <div class="answer-content">
        <div
          v-html="content"
          class="answer-content-value"
          v-if="!this.updateEditFlag"
        ></div>
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
            {{ answer.recommendedCount }}
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
            {{ answer.nonRecommendedCount }}
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
      content: this.answer.content.split("\n").join("<br />")
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser", "fetchedMyAnswerRecommendation"]),
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
      await this.$store.dispatch("UPDATE_ANSWER", {
        answerId: this.answer.id,
        updateContent: this.updateContent
      });
      this.updateEditFlag = !this.updateEditFlag;
      this.content = this.updateContent.split("\n").join("<br />");
    },
    async onAnswerRecommendation(priorType, newType) {
      if (!this.loginUser.id) {
        console.log("you should login");
        return;
      }
      if (this.isCreateOrUpdateRecommendation(priorType)) {
        await this.$store.dispatch("ON_ANSWER_RECOMMENDATION", {
          answerId: this.answer.id,
          recommendationType: newType
        });
        this.userRecommended = newType;
      } else {
        await this.$store.dispatch(
          "DELETE_ANSWER_RECOMMENDATION",
          this.answer.id
        );
        this.userRecommended = "NOT_EXIST";
      }
      await this.$store.dispatch("FETCH_ANSWERS", this.$route.params.id);
    },
    async fetchMyAnswerRecommendation(answerId, userId) {
      await this.$store.dispatch("FETCH_MY_ANSWER_RECOMMENDATION", {
        answerId,
        userId
      });
      this.userRecommended = this.myAnswerRecommendation.recommendationType;
    },
    isCreateOrUpdateRecommendation(priorType) {
      return (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      );
    },
    isUserRecommendation(recommendationType) {
      return (
        this.myAnswerRecommendation &&
        this.userRecommended === recommendationType
      );
    }
  },
  watch: {
    fetchedLoginUser() {
      this.loginUser = this.fetchedLoginUser;
      if (!this.loginUser.id) {
        this.userRecommended = "NOT_EXIST";
        this.isAuthor();
        return;
      }
      this.fetchMyAnswerRecommendation(this.answer.id, this.loginUser.id);
      this.isAuthor();
    }
  },
  async created() {
    this.loginUser = this.fetchedLoginUser;
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
