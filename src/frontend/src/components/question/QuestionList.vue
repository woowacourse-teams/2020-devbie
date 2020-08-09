<template>
  <div>
    <ul class="question-list">
      <li
        v-for="question in fetchedQuestions.questions"
        v-bind:key="question.id"
        class="question"
      >
        <p class="visits">조회수 : {{ question.visits }}</p>
        <router-link :to="`/questions/${question.questionId}`" class="title">
          Q. {{ question.title }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedQuestions"])
  },
  created() {
    const hashtag = this.$route.query.hashtag;
    if (hashtag) {
      this.$store.dispatch("FETCH_QUESTIONS_BY_HASHTAG", hashtag);
      return;
    }
    this.$store.dispatch("FETCH_QUESTIONS");
  }
};
</script>

<style scoped>
.question-list {
  list-style: none;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.question {
  border: solid 2px #87bdd6;
  border-radius: 10px;
  margin-bottom: 15px;
  padding: 10px;
  display: flex;
  align-items: center;
  width: 95%;
}

.visits {
  font-size: 14px;
  margin-right: 13px;
  margin-bottom: 0;
}

.title {
  color: #7ec699;
  font-weight: normal;
  font-size: 24px;
  text-decoration: none;
}
</style>
