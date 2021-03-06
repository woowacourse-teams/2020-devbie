<template>
  <div class="inner" :class="$mq">
    <div class="hashtag-header" v-if="hashtag">
      "<span>{{ hashtag }}</span
      >" 태그로 검색한 결과입니다.
    </div>
    <search-bar v-else></search-bar>
    <ul v-scroll="onScroll" class="question-items" :class="$mq">
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
        :class="$mq"
      >
        <div class="count-infos" :class="$mq">
          <p class="count visits" :class="$mq">
            <i class="fas fa-eye"></i> {{ question.visits }}
          </p>
          <p class="count recommendedCount" :class="$mq">
            <i class="far fa-thumbs-up"></i> {{ question.recommendedCount }}
          </p>
        </div>
        <div class="question-hashtag" :class="$mq">
          <div class="question-favorite" :class="$mq">
            <p
              @click="$router.push(`/questions/${question.id}`)"
              class="question-title"
              :class="$mq"
            >
              Q. {{ question.title }}
            </p>
            <favorite-control
              :targetObjectId="question.id"
              :isUserFavorite="isUserQuestionFavorites(question.id)"
              :isQuestion="true"
            ></favorite-control>
          </div>
          <div class="hashtag-group" :class="$mq">
            <div
              class="hashtags"
              :class="$mq"
              v-for="hashtag in question.hashtags"
              :key="hashtag.id"
            >
              #{{ hashtag.tagName }}
            </div>
          </div>
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
import FavoriteControl from "../favorite/FavoriteControl";

export default {
  components: { SearchBar, FavoriteControl },

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
      "fetchedQuestionLastPage",
      "isLoggedIn",
      "fetchedLoginUser",
      "fetchedQuestionFavorites",
      "isUserQuestionFavorites"
    ]),

    compoundKeyword() {
      return [this.title, this.content].join();
    }
  },

  watch: {
    fetchedQuestionPage() {
      this.isReady = true;
    },

    isLoggedIn() {
      this.initFavoriteState();
    },

    async hashtag() {
      await this.$store.commit("INIT_QUESTIONS");
      if (this.hashtag) {
        await this.addQuestionByHashtag();
        return;
      }
      await this.addQuestions();
    },

    async orderBy() {
      await this.$store.commit("INIT_QUESTIONS");
      await this.addQuestions();
    },

    async compoundKeyword() {
      await this.$store.commit("INIT_QUESTIONS");
      if (this.title || this.content) {
        await this.addQuestions();
      }
    }
  },

  async mounted() {
    await this.$store.commit("INIT_QUESTIONS");
    await this.addQuestions();
    if (this.hashtag) {
      await this.$store.commit("INIT_QUESTIONS");
      await this.addQuestionByHashtag();
    }
    if (!this.isLoggedIn) {
      this.$store.commit("DELETE_QUESTION_FAVORITES");
    }
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

      if (this.isLoggedIn) {
        await this.initFavoriteState();
      }
    },

    async addQuestionByHashtag() {
      try {
        await this.$store.commit("INIT_QUESTIONS");
        await this.$store.dispatch("FETCH_QUESTIONS_BY_HASHTAG", this.hashtag);
      } catch (error) {
        console.log("해시태그로 질문 조회 실패");
        await this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "질문을 불러오지 못했습니다."
        );
      }
    },

    async initFavoriteState() {
      await this.$store.dispatch("FETCH_LOGIN_USER");
      await this.$store.dispatch("FETCH_MY_FAVORITES", {
        userId: this.fetchedLoginUser.id,
        object: "question"
      });
    }
  }
};
</script>

<style scoped>
.inner {
  margin: 27px auto;
  border-left: solid 1px #e8e8e8;
}

.inner.mobile {
  border-left: none;
}

.question-items {
  list-style: none;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.question-items.mobile {
  padding: 0;
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

.question.mobile {
  padding: 0;
  max-height: 70px;
}

.count-infos {
  display: flex;
  flex-direction: column;
  min-width: 95px;
}

.count-infos.mobile {
  min-width: 50px;
}

.count {
  font-size: 14px;
  margin-right: 17px;
  margin-bottom: 0;
}

.count.mobile {
  font-size: 12px;
}

.question-title {
  color: #35495e;
  text-decoration: none;
  margin-bottom: 0;
  margin-right: 7px;
  display: flex;
  align-items: center;
}

.question-title:hover {
  cursor: pointer;
  font-weight: bold;
  text-decoration: underline;
}

.question-title.mobile {
  display: flex;
  align-items: center;
  font-size: 0.9em;
  margin: 0;
}

.question-hashtag {
  display: flex;
}

.question-hashtag.mobile {
  display: flex;
  flex-direction: column;
}

.question-favorite {
  display: flex;
}

.question-favorite.mobile {
  display: flex;
}

.hashtags {
  margin: 0 3px;
  font-size: 0.7em;
  color: #60c5ba;
  display: flex;
  align-items: center;
}

.hashtags:first-child {
  margin-left: 7px;
}

.hashtag-group {
  display: flex;
}

.hashtag-group.mobile {
  display: flex;
}

.hashtags.mobile {
  display: flex;
  align-items: center;
}

.hashtag-header {
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
