<template>
  <div class="question-form">
    <div class="inner">
      <v-card max-width="700" color="#CFE1E8" class="inner-card">
        <v-text-field
          class="input-box"
          label="질문 제목"
          counter="30"
          v-model="title"
          autofocus
          required
        ></v-text-field>
        <v-textarea
          class="input-box"
          label="질문 내용"
          v-model="content"
          counter
          no-resize
          clearable
          rows="2"
          row-height="1"
          required
        ></v-textarea>
        <div class="control-box">
          <hashtag-box @hashtags="receiveHashtags"></hashtag-box>
          <v-card-actions>
            <v-spacer></v-spacer>
            <router-link :to="`/questions`" class="form-link">
              <v-btn class="form-btn" color="blue darken-1">돌아가기 </v-btn>
            </router-link>
            <v-btn
              class="form-btn"
              color="blue darken-1"
              @click="onUpdateQuestion"
              >수정하기
            </v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import HashtagBox from "./HashtagBox";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      questionId: this.$route.params.id,
      title: "",
      content: "",
      hashtags: []
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser", "fetchedQuestion"])
  },
  methods: {
    async onUpdateQuestion() {
      await this.$store.dispatch("UPDATE_QUESTION", {
        title: this.title,
        content: this.content,
        hashtags: this.hashtags,
        questionId: this.questionId
      });
      window.location.href = `/questions/${this.questionId}`;
    },
    receiveHashtags(hashtags) {
      this.hashtags = hashtags;
    }
  },
  watch: {
    fetchedLoginUser: function() {
      this.title = this.fetchedQuestion.title;
      this.content = this.fetchedQuestion.content;
      this.hashtags = this.fetchedQuestion.hashtags;
    }
  },
  async created() {
    await this.$store.dispatch("FETCH_QUESTION", this.questionId);
    this.title = this.fetchedQuestion.title;
    this.content = this.fetchedQuestion.content;
    this.hashtags = this.fetchedQuestion.hashtags;
  },
  components: {
    HashtagBox
  }
};
</script>

<style scoped>
.question-form {
  display: flex;
  justify-content: center;
}

.inner {
  width: 600px;
}

.inner .inner-card {
  padding: 25px;
}

.control-box {
  display: flex;
  justify-content: space-between;
}

.input-box {
  margin-bottom: 25px;
}

.form-link {
  text-decoration: none;
}

.form-btn {
  margin-right: 10px;
}
</style>
