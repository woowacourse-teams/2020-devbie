<template>
  <div class="question-form">
    <div class="inner">
      <v-card max-width="700" color="#CFE1E8" class="inner-card">
        <v-text-field
          class="input-box"
          label="질문 제목"
          counter="30"
          autofocus
          required
          v-model="title"
        ></v-text-field>
        <v-textarea
          class="input-box"
          label="질문 내용"
          counter
          no-resize
          clearable
          rows="2"
          row-height="1"
          required
          v-model="content"
        ></v-textarea>
        <div class="control-box">
          <hashtag-box @hashtags="receiveHashtags"></hashtag-box>
          <form-button>
            <button class="button" @click="onCreateQuestion">
              <span>질문하기</span>
            </button>
          </form-button>
        </div>
      </v-card>
    </div>
  </div>
</template>
<script>
import HashtagBox from "./HashtagBox";
import FormButton from "./FormButton";

export default {
  data() {
    return {
      title: "",
      content: "",
      hashtags: []
    };
  },
  methods: {
    async onCreateQuestion() {
      await this.$store.dispatch("CREATE_QUESTION", {
        title: this.title,
        content: this.content,
        hashtags: this.hashtags
      });
      await this.$router.push(
        `/questions/${this.$store.getters.fetchedNewCreatedQuestionId}`
      );
    },
    receiveHashtags(hashtags) {
      this.hashtags = hashtags;
    }
  },
  created() {
    this.$store.commit("CLEAR_HASHTAGS");
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
  width: 600px;
}

.inner .inner-card {
  padding: 25px;
}

.input-box {
  margin-bottom: 25px;
}

.control-box {
  display: flex;
  justify-content: space-between;
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
