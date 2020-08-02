<template>
  <div class="answer-item-box">
    <div class="author-name">작성자: {{ answer.userId }}</div>
    <div class="answer-temp">
      <div class="answer-content">
        <p class="answer-content-value" v-if="!this.updateEditFlag">
          {{ answer.content }}
        </p>
        <v-textarea outlined v-else v-model="answer.content"></v-textarea>
      </div>
      <div :class="{ 'vertical-center': !author }" class="answer-infos">
        <div class="recommendations">
          <p class="infos">
            <i
              :class="{
                'recommendation-clicked':
                  fetchedMyAnswerRecommendation.recommendationType &&
                  userRecommended === 'RECOMMENDED'
              }"
              class="far fa-thumbs-up recommendation"
              @click="onAnswerRecommendation('NON_RECOMMENDED', 'RECOMMENDED')"
            ></i>
            {{
              answerRecommendation && answerRecommendation.data.recommendedCount
            }}
          </p>
          <p class="infos">
            <i
              :class="{
                'recommendation-clicked':
                  fetchedMyAnswerRecommendation.recommendationType &&
                  userRecommended === 'NON_RECOMMENDED'
              }"
              class="far fa-thumbs-down recommendation"
              @click="onAnswerRecommendation('RECOMMENDED', 'NON_RECOMMENDED')"
            ></i>
            {{
              answerRecommendation &&
                answerRecommendation.data.nonRecommendedCount
            }}
          </p>
        </div>
        <div v-if="author">
          <v-btn class="update-btn" v-if="this.updateEditFlag" @click="update">
            수정 확인
          </v-btn>
          <v-btn class="update-btn" v-else @click="updateBtnHandler"
            >수정</v-btn
          >
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
      author: false,
      userRecommended: "",
      updateEditFlag: false
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
      return (this.author = this.answer.userId === this.fetchedLoginUser.id);
    },
    deleteBtnHandler() {
      this.$store.dispatch("DELETE_ANSWER", this.answer.id);
    },
    updateBtnHandler() {
      this.updateEditFlag = !this.updateEditFlag;
    },
    async onAnswerRecommendation(priorType, newType) {
      const answerId = this.answer.id;
      if (
        this.userRecommended === "NOT_EXIST" ||
        this.userRecommended === priorType
      ) {
        const request = {
          recommendationType: newType
        };
        await this.$store.dispatch("ON_ANSWER_RECOMMENDATION", {
          answerId,
          request
        });
        this.userRecommended = newType;
      } else {
        await this.$store.dispatch("DELETE_ANSWER_RECOMMENDATION", answerId);
        this.userRecommended = "NOT_EXIST";
      }
      await this.$store.dispatch("FETCH_ANSWER_RECOMMENDATION", answerId);
    },
    async fetchMyAnswerRecommendation(answerId, userId) {
      await this.$store.dispatch("FETCH_MY_ANSWER_RECOMMENDATION", {
        answerId,
        userId
      });
      this.userRecommended = this.myAnswerRecommendation.data.recommendationType;
    }
  },
  watch: {
    fetchedLoginUser: async function() {
      await this.fetchMyAnswerRecommendation(
        this.answer.id,
        this.fetchedLoginUser.id
      );
    }
  },
  async created() {
    if (this.fetchedLoginUser.id) {
      await this.fetchMyAnswerRecommendation(
        this.answer.id,
        this.fetchedLoginUser.id
      );
    }
    await this.$store.dispatch("FETCH_ANSWER_RECOMMENDATION", this.answer.id);
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
.update-btn {
  margin-right: 7px;
}
.answer-content-value {
  max-width: 1100px;
}
.vertical-center {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
