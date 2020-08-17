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
  methods: {
    receiveHashtags(hashtags) {
      this.hashtags = hashtags;
    },
    async onCreateQuestion() {
      await this.$store.dispatch("CREATE_QUESTION", this.payload);
      await this.$router.push(
        `/questions/${this.$store.getters.fetchedQuestionId}`
      );
    },
    async onUpdateQuestion() {
      await this.$store.dispatch("UPDATE_QUESTION", {
        questionId: this.questionId,
        ...this.payload
      });
      await this.$router.push(`/questions/${this.questionId}`);
    }
  },
  async created() {
    if (this.editingFlag) {
      await this.$store.dispatch("FETCH_QUESTION", this.questionId);
      this.title = this.fetchedQuestion.title;
      this.content = this.fetchedQuestion.content;
      this.hashtags = this.fetchedQuestion.hashtags.map(h => h.tagName);
      return;
    }
    this.$store.commit("CLEAR_HASHTAGS");
  },
  components: {
    HashtagBox,
    FormButton
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
