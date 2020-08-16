<template>
  <div class="answer-box">
    <div class="inner">
      <div id="count-of-answer">
        {{ "답변 수 : " + fetchedAnswers.length }}
      </div>
      <div class="answer-list">
        <answer-item
          v-for="answer in fetchedAnswers"
          v-bind:key="answer.id"
          v-bind:answer="answer"
        ></answer-item>
      </div>
    </div>
  </div>
</template>

<script>
import AnswerItem from "./AnswerItem.vue";
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedAnswers"])
  },
  async created() {
    await this.$store.dispatch("FETCH_ANSWERS", this.$route.params.id);
  },
  components: {
    AnswerItem
  }
};
</script>

<style scoped>
.answer-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 100%;
}

#count-of-answer {
  padding: 11px;
  border-radius: 15px;
  background-color: #daebea;
  display: inline-block;
}

.answer-list {
  list-style: none;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}

.inner {
  width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
}
</style>
