<template>
  <div class="interview">
    <question-filters id="question-filters"></question-filters>
    <question-list id="question-list"></question-list>
    <router-link id="question-create" :to="`/create-questions`"
      ><v-btn color="#DAEBEA">질문 올리기</v-btn>
    </router-link>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import QuestionFilters from "../components/QuestionFilters";
import QuestionList from "../components/QuestionList";

export default {
  components: {
    QuestionFilters,
    QuestionList
  },
  computed: {
    ...mapGetters(["fetchedQuestions"])
  },
  created() {
    this.$store.dispatch("FETCH_QUESTIONS");
  }
};
</script>

<style scoped>
.interview {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  max-width: 95%;
  margin: 0 auto;
}
#question-filters {
  flex-grow: 1;
}
#question-list {
  flex-grow: 10;
}
#question-create {
  flex-grow: 1;
  margin-top: 40px;
  display: flex;
  justify-content: center;
  text-decoration: none;
}
</style>
