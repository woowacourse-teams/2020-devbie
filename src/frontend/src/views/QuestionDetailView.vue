<template>
  <div class="detail">
    <div class="left-menu">
      <router-link :to="`/questions`"
        ><v-btn color="#DAEBEA" class="menu-btn">돌아가기</v-btn></router-link
      >
      <router-link :to="`/edit-question/${this.$route.params.id}`"
        ><v-btn color="#DAEBEA" class="menu-btn">수정하기</v-btn></router-link
      >
    </div>
    <question-detail id="question-detail"></question-detail>
  </div>
</template>

<script>
import QuestionDetail from "../components/QuestionDetail";

export default {
  components: {
    QuestionDetail
  }
};
</script>

<style scoped>
a {
  text-decoration: none;
}
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.menu-btn {
  margin-top: 30px;
}
#question-detail {
  flex-grow: 8;
}
</style>
