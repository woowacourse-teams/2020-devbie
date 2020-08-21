<template>
  <div class="answer-create-item-box">
    <v-md-editor v-model="content" class="editor" height="350px"></v-md-editor>
    <v-btn color="#E8E8E8" class="create-btn button" @click="createAnswer"
      >답변하기</v-btn
    >
  </div>
</template>

<script>
export default {
  data: function() {
    return {
      content: ""
    };
  },

  methods: {
    createAnswer: async function() {
      if (this.content.trim() === "") {
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "답변을 채워주세요.");
        return;
      }
      try {
        await this.$store.dispatch("CREATE_ANSWER", {
          questionId: this.$route.params.id,
          content: this.content
        });
      } catch (error) {
        console.log(error);
        if (error.response.status === 401) {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "로그인 후 사용할 수 있습니다."
          );
        } else if (error.response.status === 405) {
          this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "답변을 채워주세요.");
        } else {
          this.$store.dispatch(
            "UPDATE_SNACKBAR_TEXT",
            "요청 실패했습니다. 원인 : " + error.response.data.message
          );
        }
        return;
      }
      this.content = "";
    }
  }
};
</script>

<style scoped>
.editor {
  max-width: 95%;
}

.answer-create-item-box {
  width: 95%;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
  align-self: center;
  display: flex;
  flex-direction: column;
}

.create-btn {
  width: 95%;
}

.create-btn:hover {
  background-color: #99f19e !important;
}
</style>
