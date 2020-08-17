<template>
  <div class="answer-item-box">
    <div class="author-name">작성자: {{ answer.userId }}</div>
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
      <div :class="{ 'vertical-center': !isAuthor }" class="answer-infos">
        <recommendation-control
          :targetObject="answer"
          :isQuestion="false"
        ></recommendation-control>
        <div v-if="isAuthor">
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
