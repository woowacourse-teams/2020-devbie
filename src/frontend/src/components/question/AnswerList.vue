<template>
  <div class="answer-box">
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
  </div>
</template>

<script>
import AnswerItem from "./AnswerItem.vue";
import { mapGetters } from "vuex";

export default {
  props: ["loginUser"],
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
  margin-left: 20px;
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
  width: 95%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
}
</style>
