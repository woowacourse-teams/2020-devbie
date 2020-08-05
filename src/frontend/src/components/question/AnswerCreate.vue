<template>
  <div class="answer-create-item-box">
    <v-textarea
      class="input-box"
      label="답변 내용"
      counter
      no-resize
      clearable
      row-height="1"
      required
      v-model="content"
    ></v-textarea>
    <v-btn class="create-btn" @click="createAnswer">답변하기</v-btn>
  </div>
</template>

<script>
export default {
  name: "AnswerCreateFrom",
  data: function() {
    return {
      content: ""
    };
  },
  methods: {
    createAnswer: async function() {
      await this.$store.dispatch("CREATE_ANSWER", {
        questionId: this.$route.params.id,
        content: this.content
      });
      this.content = "";
    }
  }
};
</script>

<style scoped>
.answer-create-item-box {
  width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
  align-self: center;
}

.create-btn {
  float: right;
  max-width: 10%;
  margin-top: 10px;
  padding-right: 16px;
}
</style>
