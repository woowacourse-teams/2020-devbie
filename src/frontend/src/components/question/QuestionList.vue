<template>
  <div>
    <div class="hashtag-header" v-if="hashtag">
      "<span>{{ hashtag }}</span
      >" 태그로 검색한 결과입니다.
    </div>
    <search-bar v-else :searchBy="searchBy"></search-bar>
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
        <p @click="$router.push(`/questions/${question.id}`)" class="title">
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
import SearchBar from "./SearchBar";
export default {
  components: {
    SearchBar
  },

  data() {
    return {
      hashtag: this.$route.query.hashtag,
      orderBy: this.$route.query.orderBy || "CREATED_DATE",
      searchBy: this.$route.query.searchBy
    };
  },

  computed: {
    ...mapGetters(["fetchedQuestions"])
  },

  created() {
    if (this.hashtag) {
      this.$store.dispatch("FETCH_QUESTIONS_BY_HASHTAG", this.hashtag);
      return;
    }
    this.$store.dispatch("FETCH_QUESTIONS", this.orderBy);
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
  border-top: solid 2px #87bdd6;
  min-height: 120px;
  padding: 10px;
  display: flex;
  align-items: center;
  width: 95%;
}

.question:last-child {
  border-bottom: solid 2px #87bdd6;
}

.count-infos {
  display: flex;
  flex-direction: column;
  min-width: 95px;
}

.count {
  font-size: 14px;
  margin-right: 17px;
  margin-bottom: 0;
}

.title {
  color: #35495e;
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
  color: #60c5ba;
}

.hashtags:first-child {
  margin-left: 7px;
}

.hashtag-header {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
}

.hashtag-header span {
  color: #fc8c84;
  font-size: 26px;
}
</style>
