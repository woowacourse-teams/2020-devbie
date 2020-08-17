<template>
  <div class="question-detail">
    <div class="inner">
      <div class="question-header">
        <div class="question-title">
          <h1>Q. {{ fetchedQuestion.title }}</h1>
        </div>
        <div class="question-header-bottom">
          <div class="hashtags">
            <span
              v-for="hashtag in fetchedQuestion.hashtags"
              v-bind:key="hashtag.id"
              class="hashtag"
              @click="$router.push(`/questions?hashtag=${hashtag.tagName}`)"
              >#{{ hashtag.tagName }}
            </span>
          </div>
          <div class="question-info">
            <p class="infos">
              <i class="fas fa-user-edit"></i>
              {{ loginUser.name }}
            </p>
            <p class="infos">
              <i class="fas fa-eye"></i>
              {{ fetchedQuestion.visits }}
            </p>
            <recommendation-control
              :targetObject="fetchedQuestion"
              :isQuestion="true"
            ></recommendation-control>
          </div>
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
import MarkdownContent from "./MarkdownContent";
import RecommendationControl from "./RecommendationControl";

export default {
  props: ["loginUser", "fetchedQuestion"],
  data() {
    return {
      content: ""
    };
  },
  watch: {
    fetchedQuestion() {
      this.content = this.fetchedQuestion.content;
    }
  },
  components: {
    MarkdownContent,
    RecommendationControl
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

.question-title {
  margin-bottom: 12px;
}

.question-header {
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
}

.question-header-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.hashtag {
  margin: 0 9px;
  color: #0086b3;
}

.hashtag:hover {
  cursor: pointer;
  font-weight: bold;
  color: #445588;
  text-decoration: underline;
}

.question-info {
  min-width: 180px;
  display: flex;
  justify-content: flex-end;
}

.question-info .infos {
  font-size: 16px;
  margin-right: 15px;
  margin-bottom: 0;
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
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
  height: 400px;
}
</style>
