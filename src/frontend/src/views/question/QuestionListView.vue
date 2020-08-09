<template>
  <div class="interview">
    <div class="inner">
      <question-filters id="question-filters"></question-filters>
      <question-list id="question-list"></question-list>
      <question-control id="question-control"></question-control>
    </div>
  </div>
</template>

<script>
import QuestionFilters from "../../components/question/QuestionFilters";
import QuestionList from "../../components/question/QuestionList";
import QuestionControl from "../../components/question/QuestionControl";

export default {
  components: {
    QuestionFilters,
    QuestionList,
    QuestionControl
  }
};
</script>

<style scoped>
.interview {
  display: flex;
  justify-content: center;
  margin: 0;
}

.inner {
  width: 95%;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

#question-filters {
  flex-grow: 1;
}

#question-list {
  flex-grow: 10;
}

#question-control {
  flex-grow: 1;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  text-decoration: none;
}
</style>
