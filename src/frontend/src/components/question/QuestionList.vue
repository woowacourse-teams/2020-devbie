<template>
  <div>
    <ul class="question-list">
      <li
        v-for="question in fetchedQuestions.questions"
        v-bind:key="question.id"
        class="question"
      >
        <div class="count-infos">
          <p class="count visits">조회수 : {{ question.visits }}</p>
          <p class="count recommendedCount">
            추천수 : {{ question.recommendedCount }}
          </p>
        </div>
        <p
          @click="$router.push(`/questions/${question.questionId}`)"
          class="title"
        >
          Q. {{ question.title }}
        </p>
        <div
          class="hashtags"
          v-for="hashtag in question.hashtags"
          :key="hashtag.id"
        >
          #{{ hashtag.tagName }}
        </div>
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
    const orderBy = this.$route.query.orderBy || "CREATED_DATE";
    if (hashtag) {
      this.$store.dispatch("FETCH_QUESTIONS_BY_HASHTAG", hashtag);
      return;
    }
    this.$store.dispatch("FETCH_QUESTIONS", orderBy);
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

.count-infos {
  display: flex;
  flex-direction: column;
}

.count {
  font-size: 14px;
  margin-right: 17px;
  margin-bottom: 0;
}

.title {
  color: #35495e;
  font-weight: normal;
  font-size: 24px;
  text-decoration: none;
  margin-bottom: 0;
  margin-right: 7px;
}

.title:hover {
  cursor: pointer;
  font-weight: bold;
  text-decoration: underline;
}

.hashtags {
  margin: 0 3px;
  font-size: 13px;
}

.hashtags:first-child {
  margin-left: 7px;
}
</style>
