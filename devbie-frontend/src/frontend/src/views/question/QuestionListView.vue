<template>
  <div class="interview" :class="$mq">
    <div class="question-menu" :class="$mq">
      <question-filters
        class="question-filters"
        :class="$mq"
      ></question-filters>
      <question-control
        class="question-control"
        :class="$mq"
      ></question-control>
    </div>
    <question-list
      :orderBy="orderBy"
      :title="title"
      :content="content"
      :hashtag="hashtag"
      class="question-list"
      :class="$mq"
    ></question-list>
  </div>
</template>

<script>
import QuestionFilters from "../../components/question/QuestionFilters";
import QuestionControl from "../../components/question/QuestionControl";
import QuestionList from "../../components/question/QuestionList";

export default {
  components: {
    QuestionFilters,
    QuestionControl,
    QuestionList
  },

  props: ["orderBy", "title", "content", "hashtag"]
};
</script>

<style scoped>
.interview {
  margin: 0 auto;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

.interview.mobile {
  max-width: 95%;
  flex-direction: column;
  align-items: center;
}

.question-menu {
  max-width: 200px;
  flex-grow: 1;
}

.question-menu.tablet {
  min-width: 150px;
}

.question-menu.mobile {
  min-width: 80% !important;
}

.question-filters.mobile {
  flex-direction: row;
  justify-content: space-between !important;
}

.question-control {
  margin-top: 20px;
}

.question-control.mobile {
  flex-direction: row;
  margin: 20px auto 0 auto;
  justify-content: space-between !important;
}

.question-list {
  flex-grow: 2;
}
</style>
