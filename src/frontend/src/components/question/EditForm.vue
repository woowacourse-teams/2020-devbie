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
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      questionId: this.$route.params.id,
      title: this.$store.getters.fetchedQuestion.title,
      content: this.$store.getters.fetchedQuestion.content
    };
  },
  methods: {
    async onUpdateQuestion() {
      if (this.title === "" || this.content === "") {
        this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "질문 제목과 질문 내용을 채워주세요."
        );
        return;
      }
      const request = {
        title: this.title,
        content: this.content
      };
      try {
        const id = this.questionId;
        await this.$store.dispatch("UPDATE_QUESTION", { request, id });
        window.location.href = `/questions/${this.questionId}`;
      } catch (error) {
        console.log(error);
        if (error.response.status === 405) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "질문 제목과 질문 내용을 채워주세요."
          );
        } else if (error.response.status === 403) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "작성자만 수정할 수 있습니다."
          );
        } else {
          this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "요청에 실패했습니다.");
        }
      }
    }
  },
  created() {
    this.$store.dispatch("FETCH_QUESTION", this.questionId);
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

.form-link {
  text-decoration: none;
}

.form-btn {
  margin-right: 10px;
}
</style>
