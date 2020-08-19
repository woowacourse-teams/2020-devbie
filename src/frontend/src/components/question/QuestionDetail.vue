<template>
  <div class="question-detail">
    <div class="inner">
      <div class="question-header">
        <div class="left-container">
          <h1>Q. {{ fetchedQuestion.title }}</h1>
          <div class="hashtags">
            <span
              v-for="hashtag in fetchedQuestion.hashtags"
              v-bind:key="hashtag.id"
              class="hashtag"
              @click="$router.push(`/questions?hashtag=${hashtag.tagName}`)"
              >#{{ hashtag.tagName }}
            </span>
          </div>
        </div>
        <div class="right-container">
          <div class="question-info">
            <p class="infos">
              <i class="fas fa-user-edit"></i>
              {{ fetchedQuestion.author }}
            </p>
            <p class="infos">
              <i class="fas fa-eye"></i>
              {{ fetchedQuestion.visits }}
            </p>
          </div>
          <recommendation-control
            :targetObject="fetchedQuestion"
            :isQuestion="true"
          ></recommendation-control>
        </div>
      </div>
      <markdown-content
        class="question-content"
        :content="content"
      ></markdown-content>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import MarkdownContent from "./MarkdownContent";
import RecommendationControl from "./RecommendationControl";

export default {
  components: {
    MarkdownContent,
    RecommendationControl
  },

  props: ["loginUser"],

  data() {
    return {
      content: ""
    };
  },

  computed: {
    ...mapGetters(["fetchedQuestion"])
  },

  created() {
    this.fetchCurrentQuestion();
  },

  methods: {
    async fetchCurrentQuestion() {
      await this.$store.dispatch("FETCH_QUESTION", this.$route.params.id);
      this.content = this.fetchedQuestion.content;
    }
  }
};
</script>

<style scoped>
.question-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
}

.left-container h1 {
  margin-bottom: 12px;
}

.right-container {
  min-width: 190px;
  margin-top: 7px;
}

.hashtag {
  margin: 3px 9px;
  color: #0086b3;
}

.hashtag:hover {
  cursor: pointer;
  font-weight: bold;
  color: #fc8c84;
  text-decoration: underline;
}

.question-info {
  min-width: 180px;
  display: flex;
  justify-content: center;
  margin-bottom: 6px;
}

.question-info .infos {
  font-size: 17px;
  margin-right: 15px;
  margin-bottom: 0;
  color: #6d819c;
}

.question-info .infos:last-child {
  margin-right: 5px;
}

.question-content {
  padding: 30px 50px;
  margin-bottom: 50px;
}

.inner {
  width: 95%;
  box-sizing: border-box;
  padding: 10px 0 30px 0;
  border-bottom: solid 1px #e8e8e8;
  min-height: 400px;
}
</style>
