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
        <v-card-actions>
          <v-spacer></v-spacer>
          <router-link :to="`/questions`" class="form-link">
            <v-btn class="form-btn" color="blue darken-1">돌아가기 </v-btn>
          </router-link>
          <v-btn
            class="form-btn"
            color="blue darken-1"
            @click="onCreateQuestion"
            >질문 올리기
          </v-btn>
        </v-card-actions>
      </v-card>
      <v-snackbar v-model="snackbar" :multi-line="true" top>
        {{ snackbarText }}

        <template v-slot:action="{ attrs }">
          <v-btn color="white" text v-bind="attrs" @click="snackbar = false">
            닫기
          </v-btn>
        </template>
      </v-snackbar>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      title: "",
      content: "",
      snackbar: false,
      snackbarText: ""
    };
  },
  methods: {
    async onCreateQuestion() {
      if (this.title === "" || this.content === "") {
        this.snackbar = true;
        this.snackbarText = "질문 제목과 질문 내용을 채워주세요.";
        return;
      }
      const request = {
        title: this.title,
        content: this.content
      };
      try {
        await this.$store.dispatch("CREATE_QUESTION", request);
        window.location.href = `/questions/${this.$store.getters.fetchedNewCreatedQuestionId}`;
      } catch (error) {
        this.snackbar = true;
        this.snackbarText = "질문 제목과 질문 내용을 채워주세요.";
        console.log(error);
      }
    }
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
