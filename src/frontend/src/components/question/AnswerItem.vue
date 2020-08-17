<template>
  <div class="answer-item-box">
    <div class="left-container">
      <div class="author-name">
        <p class="infos">
          <i class="fas fa-user-edit"></i>
          작성자: {{ answer.userId }}
        </p>
      </div>
      <div class="answer-temp">
        <div class="answer-content">
          <markdown-content
            class="answer-content-value"
            v-if="!this.updateEditFlag"
            :content="content"
          ></markdown-content>
          <v-textarea
            class="update-form"
            outlined
            v-else
            v-model="content"
          ></v-textarea>
        </div>
      </div>
    </div>
    <div class="right-container">
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
</template>

<script>
import MarkdownContent from "./MarkdownContent";
import RecommendationControl from "../../components/question/RecommendationControl";

export default {
  props: ["answer", "loginUser"],
  data: function() {
    return {
      updateEditFlag: false,
      content: this.answer.content
    };
  },
  computed: {
    isAuthor() {
      return this.answer.userId === this.loginUser.id;
    }
  },
  methods: {
    async deleteBtnHandler() {
      await this.$store.dispatch("DELETE_ANSWER", this.answer.id);
    },
    updateBtnHandler() {
      this.updateEditFlag = !this.updateEditFlag;
    },
    async update() {
      await this.$store.dispatch("UPDATE_ANSWER", {
        answerId: this.answer.id,
        updateContent: this.content
      });
      this.updateEditFlag = !this.updateEditFlag;
    }
  },
  components: {
    MarkdownContent,
    RecommendationControl
  }
};
</script>

<style scoped>
.author-name {
  color: #6d819c;
  margin-bottom: 6px;
}

.author-name .infos {
  font-size: 14px;
  margin: 0 30px;
}

.answer-item-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
  border-bottom: solid #e8e8e8 1px;
  min-height: 150px;
}

.answer-content {
  padding: 20px 15px 30px 30px;
}

.answer-temp {
  display: flex;
  justify-content: space-between;
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
}

.answer-content {
  min-width: 80%;
}

.answer-content-value {
  max-width: 1100px;
}
</style>
