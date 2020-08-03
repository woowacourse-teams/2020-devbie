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
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      title: "",
      content: ""
    };
  },
  methods: {
    async onCreateQuestion() {
      const request = {
        title: this.title,
        content: this.content
      };
      await this.$store.dispatch("CREATE_QUESTION", request);
      window.location.href = `/questions/${this.$store.getters.fetchedNewCreatedQuestionId}`;
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
