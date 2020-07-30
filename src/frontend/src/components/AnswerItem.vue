<template>
  <div class="answer-item-box">
    <div>작성자: {{ answer.userId }}</div>
    <div class="answer-temp">
      <div class="answer-content">
        <p class="answer-content-value" v-if="!this.updateEditFlag">
          {{ answer.content }}
        </p>
        <v-textarea outlined v-else v-model="answer.content"></v-textarea>
      </div>
      <div>
        <v-btn v-if="this.updateEditFlag" @click="update">
          수정 확인
        </v-btn>
        <v-btn v-else @click="updateBtnHandler">수정</v-btn>
        <v-btn @click="deleteBtnHandler">삭제</v-btn>
      </div>
    </div>
    <hr class="answer-line"/>
  </div>
</template>

<script>
  export default {
    props: ["answer"],
    data: function () {
      return {
        updateEditFlag: false
      };
    },
    methods: {
      deleteBtnHandler: function () {
        this.$store.dispatch("DELETE_ANSWER", this.answer.id);
      },
      updateBtnHandler: function () {
        this.updateEditFlag = !this.updateEditFlag;
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
    padding: 30px 50px;
  }

  .answer-temp {
    display: flex;
    justify-content: space-between;
  }

  .answer-content-value {
    max-width: 1100px;
  }
</style>
