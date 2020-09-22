<template>
  <div class="answer-item-box" :class="{ editing: updateEditFlag }">
    <div class="upper-container">
      <div class="left-container" :class="$mq">
        <p class="infos" :class="$mq">
          <i class="fas fa-user-edit"></i>
          {{ answer.author }}
        </p>
      </div>
      <div class="right-container" :class="{ editingButton: updateEditFlag }">
        <div :class="{ 'vertical-center': !isAuthor }" class="answer-infos">
          <div class="author-button" v-if="isAuthor">
            <v-btn
              color="#DAEBEA"
              small
              class="button answer-btn"
              v-if="updateEditFlag"
              @click="update"
            >
              수정 확인
            </v-btn>
            <v-btn
              color="#DAEBEA"
              small
              class="button answer-btn"
              v-else
              @click="updateBtnHandler"
            >
              수정
            </v-btn>
            <v-btn
              color="#DAEBEA"
              class="button answer-btn delete-btn"
              small
              @click="deleteBtnHandler"
            >
              삭제
            </v-btn>
          </div>
          <recommendation-control
            :targetObject="answer"
            :isQuestion="false"
          ></recommendation-control>
        </div>
      </div>
    </div>
    <div class="answer-temp">
      <markdown-content
        class="answer-content-value"
        v-if="!updateEditFlag"
        :content="content"
      ></markdown-content>
      <v-md-editor
        v-else
        class="update-editor"
        height="250px"
        v-model="content"
      ></v-md-editor>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import MarkdownContent from "./MarkdownContent";
import RecommendationControl from "../../components/question/RecommendationControl";

export default {
  components: {
    MarkdownContent,
    RecommendationControl
  },

  props: ["answer", "loginUser"],

  data: function() {
    return {
      updateEditFlag: false,
      content: this.answer.content
    };
  },

  computed: {
    ...mapGetters(["isLoggedIn"]),
    isAuthor() {
      return this.isLoggedIn && this.answer.userId === this.loginUser.id;
    }
  },

  methods: {
    async deleteBtnHandler() {
      try {
        await this.$store.dispatch("DELETE_ANSWER", this.answer.id);
      } catch (error) {
        console.error(error);
        if (error.response.status === 401) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "로그인 정보가 확인되지 않습니다."
          );
        } else if (error.response.status === 403) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "삭제할 수 있는 권한이 없습니다."
          );
        } else {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "삭제 요청에 실패했습니다."
          );
        }
      }
    },

    updateBtnHandler() {
      this.updateEditFlag = !this.updateEditFlag;
    },

    async update() {
      if (this.content.trim() === "") {
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "답변을 채워주세요.");
        return;
      }
      try {
        await this.$store.dispatch("UPDATE_ANSWER", {
          answerId: this.answer.id,
          updateContent: this.content
        });
        this.updateEditFlag = !this.updateEditFlag;
      } catch (error) {
        console.error(error);
        console.error(error.response.data.message);
        if (error.response.status === 405) {
          this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "답변을 채워주세요.");
        } else if (error.response.status === 401) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "로그인 정보가 확인되지 않습니다."
          );
        } else if (error.response.status === 403) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "수정할 수 있는 권한이 없습니다."
          );
        } else {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "수정 요청에 실패했습니다."
          );
        }
      }
    },
    async onAnswerRecommendation(priorType, newType) {
      if (!this.loginUser.id) {
        this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "로그인 후 추천/비추천 할 수 있습니다. "
        );
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
            this.$store.dispatch(
              "UPDATE_SNACKBAR_TEXT",
              "로그인 후 추천/비추천 할 수 있습니다. "
            );
          }
        }
      } else {
        try {
          await this.$store.dispatch("DELETE_ANSWER_RECOMMENDATION", answerId);
          this.userRecommended = "NOT_EXIST";
        } catch (error) {
          console.log(error);
          if (error.response.status === 401) {
            this.$store.dispatch(
              "UPDATE_SNACKBAR_TEXT",
              "로그인 후 추천/비추천 할 수 있습니다. "
            );
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
  }
};
</script>

<style scoped>
.upper-container {
  min-width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.left-container {
  color: #6d819c;
}

.left-container.mobile {
  margin: 0;
}

.left-container .infos {
  font-size: 14px;
  margin: 0 30px;
}

.left-container .infos .mobile {
}

.answer-item-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border-bottom: solid #e8e8e8 1px;
  min-height: 150px;
}

.answer-item-box.mobile {
  flex-direction: column;
}

.editing {
  display: flex !important;
  flex-direction: column !important;
  align-items: unset !important;
  justify-content: center !important;
}

.editingButton {
  display: flex !important;
  align-items: center !important;
  justify-content: flex-end !important;
}

.update-editor {
  width: 100%;
}

.answer-temp {
  max-width: 100%;
  padding: 20px 30px;
}

.answer-infos {
  min-width: 170px;
}

.answer-btn {
  margin: 0 4px;
}

.delete-btn:hover {
  background-color: #fc9d9a !important;
}

.author-button {
  display: flex;
  justify-content: center;
  margin-bottom: 5px;
}

.answer-content-value {
  max-width: 1100px;
}
</style>
