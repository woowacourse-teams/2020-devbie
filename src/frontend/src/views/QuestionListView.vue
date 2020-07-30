<template>
  <div class="interview">
    <question-filters id="question-filters"></question-filters>
    <question-list id="question-list"></question-list>
    <v-btn
      v-if="isLoggedIn"
      id="question-create"
      @click="$router.push('/create-question')"
      color="#DAEBEA"
      >질문 올리기</v-btn
    >
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
  data() {
    return {
      isLoggedIn: false
    };
  },
  computed: {
    ...mapGetters(["fetchedQuestions", "fetchedLoginUser"])
  },
  created() {
    this.$store.dispatch("FETCH_QUESTIONS");
    this.isLoggedIn = !this.fetchedLoginUser.id;
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
