<template>
  <div class="interview">
    <question-filters id="question-filters"></question-filters>
    <question-list id="question-list"></question-list>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
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
  align-items: center;
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
</style>
