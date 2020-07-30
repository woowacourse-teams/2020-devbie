<template>
  <div class="answer-item-box">
    <div>작성자: {{ fetchedAnswer.userId }}</div>
    <div class="answer-temp">
      <div class="answer-content">
        <p class="answer-content-value" v-if="!this.updateEditFlag">
          {{ fetchedAnswer.content }}
        </p>
        <v-textarea
          outlined
          v-else
          v-model="fetchedAnswer.content"
        ></v-textarea>
      </div>
      <v-btn v-if="this.updateEditFlag" @click="update">
        수정 확인
      </v-btn>
      <v-btn v-else @click="updateBtnHandler">수정</v-btn>
      <v-btn @click="deleteBtnHandler">삭제</v-btn>
    </div>
    <hr class="answer-line" />
  </div>
</template>

<script>
export default {
  computed: {
    fetchedAnswer() {
      return this.$store.getters.fetchedAnswer(this.answerId);
    }
  },
  props: {
    answerId: Number
  },
  data: function() {
    return {
      updateEditFlag: false
    };
  },
  methods: {
    deleteBtnHandler: function() {
      this.$store.dispatch("DELETE_ANSWER", this.answerId);
      console.log(this.$store.getters.fetchedAnswers);
    },
    updateBtnHandler: function() {
      if (this.updateEditFlag === true) {
        this.update();
      }
      this.updateEditFlag = !this.updateEditFlag;
    },
    update: async function() {
      // try {
      //   axios.patch(
      //     "/api/answers/" + this.id,
      //     {
      //       content: this.content
      //     },
      //     {
      //       headers: {
      //         Authorization: "Bearer " + localStorage.getItem("devbieToken")
      //       }
      //     }
      //   );
      //   this.updateEditFlag = !this.updateEditFlag;
      // } catch (error) {
      //   console.log(error);
      // }
    }
  }
};
</script>

<style scoped>
.answer-item-box {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
}
.answer-content {
  flex-grow: 9;
  padding: 30px 50px;
}
.answer-temp {
  display: flex;
}
</style>
