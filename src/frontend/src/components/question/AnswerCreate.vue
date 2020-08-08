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
    <v-snackbar v-model="snackbar" :multi-line="true" top>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="snackbar = false">
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  name: "AnswerCreateFrom",
  data: function() {
    return {
      content: "",
      snackbar: false,
      snackbarText: ""
    };
  },
  methods: {
    createAnswer: async function() {
      try {
        await this.$store.dispatch("CREATE_ANSWER", {
          questionId: this.$route.params.id,
          content: this.content
        });
      } catch (error) {
        console.log(error);
        if (error.response.status === 401) {
          this.snackbarText = "로그인 후 사용할 수 있습니다.";
        } else if (error.response.status === 405) {
          this.snackbarText = "답변을 채워주세요.";
        } else {
          this.snackbarText =
            "요청 실패했습니다. 원인 : " + error.response.data.message;
        }
        this.snackbar = true;
      }
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
