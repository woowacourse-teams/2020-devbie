<template>
  <div class="question-form">
    <div class="inner">
      <v-text-field
        class="input-box"
        label="질문 제목"
        counter="30"
        v-model="title"
        autofocus
        required
      ></v-text-field>
      <hashtag-box
        :existHashtags="hashtags"
        @hashtags="receiveHashtags"
      ></hashtag-box>
      <div class="control-box">
        <div class="content-title">질문 내용</div>
        <v-md-editor v-model="content" height="250px"></v-md-editor>
        <form-button>
          <v-btn
            v-if="editingFlag"
            large
            color="#DAEBEA"
            class="button green-hover"
            @click="onUpdateQuestion"
          >
            <span>수정하기</span>
          </v-btn>
          <v-btn
            v-else
            large
            color="#DAEBEA"
            class="button green-hover"
            @click="onCreateQuestion"
          >
            <span>질문하기</span>
          </v-btn>
        </form-button>
      </div>
    </div>
  </div>
</template>
<script>
import HashtagBox from "./HashtagBox";
import FormButton from "./FormButton";
import { mapGetters } from "vuex";

export default {
  components: {
    HashtagBox,
    FormButton
  },

  props: ["editingFlag"],

  data() {
    return {
      questionId: this.$route.params.id,
      title: "",
      content: "",
      hashtags: []
    };
  },

  computed: {
    ...mapGetters(["fetchedQuestion", "fetchedLoginUser"]),

    payload() {
      return {
        title: this.title,
        content: this.content,
        hashtags: this.hashtags
      };
    }
  },

  created() {
    if (this.editingFlag) {
      this.fetchQuestionInfo();
      return;
    }
    this.$store.commit("CLEAR_HASHTAGS");
  },

  methods: {
    receiveHashtags(hashtags) {
      this.hashtags = hashtags;
    },

    async onCreateQuestion() {
      try {
        await this.$store.dispatch("CREATE_QUESTION", this.payload);
        await this.$router.push(
          `/questions/${this.$store.getters.fetchedQuestionId}`
        );
      } catch (error) {
        console.log(error);
        switch (error.response.status) {
          case 401: {
            this.$store.dispatch(
              "UPDATE_SNACKBAR_TEXT",
              "로그인 후 사용할 수 있습니다."
            );
            break;
          }
          case 405: {
            this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "항목을 채워주세요.");
            break;
          }
          default: {
            console.log("에러입니다" + error);
            this.$store.dispatch(
              "UPDATE_SNACKBAR_TEXT",
              "요청에 실패했습니다."
            );
            break;
          }
        }
      }
    },

    async onUpdateQuestion() {
      await this.$store.dispatch("UPDATE_QUESTION", {
        questionId: this.questionId,
        ...this.payload
      });
      await this.$router.push(`/questions/${this.questionId}`);
    },

    async fetchQuestionInfo() {
      await this.$store.dispatch("FETCH_QUESTION", this.questionId);
      this.title = this.fetchedQuestion.title;
      this.content = this.fetchedQuestion.content;
      this.hashtags = this.fetchedQuestion.hashtags.map(h => h.tagName);
    }
  }
};
</script>

<style scoped>
.content-title {
  font-size: 12.5px;
  margin: 12px 0;
}
.question-form {
  display: flex;
  justify-content: center;
}

.inner {
  min-width: 80%;
  padding: 25px;
}

.input-box {
  margin-bottom: 25px;
}
</style>