<template>
  <div>
    <div class="hashtag-header" v-if="hashtag">
      "<span>{{ hashtag }}</span
      >" 태그로 검색한 결과입니다.
    </div>
    <search-bar v-else></search-bar>
    <ul v-scroll="onScroll" class="question-list">
      <div v-if="fetchedQuestions.length === 0" class="no-result">
        <i class="fas fa-exclamation"></i>
        검색 결과가 존재하지 않습니다.
        <i class="fas fa-exclamation"></i>
      </div>
      <li
        v-else
        v-for="question in fetchedQuestions"
        :key="question.id"
        class="question"
      >
        <div class="count-infos">
          <p class="count visits">조회수 : {{ question.visits }}</p>
          <p class="count recommendedCount">
            추천수 : {{ question.recommendedCount }}
          </p>
        </div>
        <p @click="$router.push(`/questions/${question.id}`)" class="title">
          Q. {{ question.title }}
        </p>
        <div
          class="hashtags"
          v-for="hashtag in question.hashtags"
          :key="hashtag.id"
        >
          #{{ hashtag.tagName }}
        </div>
      </li>
    </ul>
    <v-progress-circular
      v-if="isBottom"
      :size="50"
      color="primary"
      indeterminate
      class="loading-progress"
    ></v-progress-circular>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import SearchBar from "./SearchBar";
export default {
  components: {
    SearchBar
  },

  props: ["orderBy", "title", "content", "hashtag"],

  data() {
    return {
      isBottom: false,
      isReady: true
    };
  },

  computed: {
    ...mapGetters([
      "fetchedQuestions",
      "fetchedQuestionPage",
      "fetchedQuestionLastPage"
    ])
  },

  watch: {
    fetchedQuestionPage() {
      this.isReady = true;
    }
  },

  async created() {
    if (this.fetchedQuestions.length > 0) {
      return;
    }

    await this.addQuestions();
  },

  methods: {
    async onScroll({ target }) {
      if (!this.isReady) {
        return;
      }

      const { scrollTop, clientHeight, scrollHeight } = target.scrollingElement;
      let clientCurrentHeight = scrollTop + clientHeight;
      let componentHeight = scrollHeight - this.$el.lastElementChild.offsetTop;
      const currentState = clientCurrentHeight > componentHeight;

      if (
        this.isBottom !== currentState &&
        this.fetchedQuestionPage <= this.fetchedQuestionLastPage
      ) {
        this.isBottom = true;
        await this.addQuestions();
        this.isBottom = false;
      }
    },

    isEndPage() {
      return this.fetchedQuestionPage > this.fetchedQuestionLastPage;
    },

    async addQuestions() {
      this.isReady = false;

      const param = {
        page: this.fetchedQuestionPage,
        orderBy: this.orderBy || "CREATED_DATE",
        title: this.title || "",
        content: this.content || ""
      };
      const queryParam = new URLSearchParams(param).toString();
      try {
        await this.$store.dispatch("FETCH_QUESTIONS", queryParam);
      } catch (error) {
        console.log("질문 리스트 불러오기 실패" + error.response.data.message);
        await this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "질문을 불러오지 못했습니다."
        );
      }
    }
  }
};
</script>

<style scoped>
.question-list {
  list-style: none;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.question {
  border-top: solid 2px #87bdd6;
  min-height: 120px;
  padding: 10px;
  display: flex;
  align-items: center;
  width: 95%;
}

.question:last-child {
  border-bottom: solid 2px #87bdd6;
  margin-bottom: 40px;
}

.count-infos {
  display: flex;
  flex-direction: column;
  min-width: 95px;
}

.count {
  font-size: 14px;
  margin-right: 17px;
  margin-bottom: 0;
}

.title {
  color: #35495e;
  text-decoration: none;
  margin-bottom: 0;
  margin-right: 7px;
}

.title:hover {
  cursor: pointer;
  font-weight: bold;
  text-decoration: underline;
}

.hashtags {
  margin: 0 3px;
  font-size: 13px;
  color: #60c5ba;
}

.hashtags:first-child {
  margin-left: 7px;
}

.hashtag-header {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
}

.hashtag-header span {
  color: #fc8c84;
  font-size: 26px;
}

.no-result {
  font-family: "Jua", sans-serif;
  margin-top: 180px;
  font-size: 25px;
}

.loading-progress {
  text-align: center;
  left: 50%;
}
</style>
