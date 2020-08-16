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
      <hashtag-box @hashtags="receiveHashtags"></hashtag-box>
      <div class="control-box">
        <v-md-editor v-model="content" height="250px"></v-md-editor>
        <form-button>
          <button class="button" @click="onUpdateQuestion">
            <span>수정하기</span>
          </button>
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
      await this.$router.push(`/questions/${this.questionId}`);
    },
    receiveHashtags(hashtags) {
      this.hashtags = hashtags;
    }
  },
  watch: {
    fetchedLoginUser() {
      this.title = this.fetchedQuestion.title;
      this.content = this.fetchedQuestion.content;
      this.hashtags = this.fetchedQuestion.hashtags.map(h => h.tagName);
    }
  },
  async created() {
    await this.$store.dispatch("FETCH_QUESTION", this.questionId);
    this.title = this.fetchedQuestion.title;
    this.content = this.fetchedQuestion.content;
    this.hashtags = this.fetchedQuestion.hashtags.map(h => h.tagName);
  },
  components: {
    HashtagBox,
    FormButton
  }
};
</script>

<style scoped>
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

.button {
  background: #fff;
  border: none;
  padding: 2px;
  cursor: pointer;
  display: block;
  position: relative;
  overflow: hidden;
  transition: all 0.35s ease-in-out 0.35s;
  margin: 0 10px;
  text-align: center;
  border-radius: 4px;
}

span {
  display: block;
  padding: 7px 14px;
  background: #fff;
  z-index: 100;
  position: relative;
  transition: all 0.35s ease-in-out 0.35s;
}

.button:hover span {
  background: #36b4c7;
  color: #fff;
  transition: all 0.35s ease-in-out 0.35s;
}

.button:after {
  bottom: -100%;
  right: -100%;
  content: "";
  width: 100%;
  height: 100%;
  position: absolute;
  background: #36b4c7;
  transition: all 0.35s ease-in-out 0.5s;
}

.button:hover:after {
  right: 0;
  bottom: 0;
  transition: all ease-in-out 0.35s;
}

.button:before {
  top: -100%;
  left: -100%;
  content: "";
  width: 100%;
  height: 100%;
  position: absolute;
  background: #36b4c7;
  transition: all 0.35s ease-in-out 0.5s;
}

.button:hover:before {
  left: 0;
  top: 0;
  transition: all ease-in-out 0.35s;
}
</style>
