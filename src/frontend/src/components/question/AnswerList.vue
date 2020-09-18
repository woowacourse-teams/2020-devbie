<template>
  <div class="inner">
    <div id="count-of-answer">
      {{ "답변 수 : " + fetchedAnswers.length }}
    </div>
    <div class="answer-list">
      <answer-item
        v-for="answer in fetchedAnswers"
        :key="answer.id"
        :answer="answer"
        :loginUser="loginUser"
      ></answer-item>
    </div>
  </div>
</template>

<script>
import AnswerItem from "./AnswerItem.vue";
import { mapGetters } from "vuex";

export default {
  components: {
    AnswerItem
  },

  props: ["loginUser"],

  computed: {
    ...mapGetters(["fetchedAnswers"])
  },

  async created() {
    await this.$store.dispatch("FETCH_ANSWERS", this.$route.params.id);
  }
};
</script>

<style scoped>
#count-of-answer {
  padding: 10px 14px 0 14px;
  border-radius: 15px;
  display: inline-block;
  color: #4ea1d3;
  font-size: 18px;
  font-family: "Jua", sans-serif;
}

.answer-list {
  list-style: none;
  display: flex;
  flex-direction: column;
}

.inner {
  min-width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  display: flex;
  flex-direction: column;
}
</style>
