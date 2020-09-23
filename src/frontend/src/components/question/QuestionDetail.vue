<template>
  <div class="inner">
    <div class="question-header" :class="$mq">
      <div class="left-container" :class="$mq">
        <div class="title" :class="$mq">
          Q. {{ fetchedQuestion.title }}
          <favorite-control
            class="favorite"
            :targetObjectId="fetchedQuestion.id"
            :isUserFavorite="isUserQuestionFavorites(fetchedQuestion.id)"
            :isQuestion="true"
          ></favorite-control>
        </div>
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
      <div class="right-container" :class="$mq">
        <div class="question-info" :class="$mq">
          <p class="infos" :class="$mq">
            <i class="fas fa-user-edit"></i>
            {{ fetchedQuestion.author }}
          </p>
          <p class="infos" :class="$mq">
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
</template>

<script>
import { mapGetters } from "vuex";
import MarkdownContent from "./MarkdownContent";
import RecommendationControl from "./RecommendationControl";
import FavoriteControl from "../favorite/FavoriteControl";

export default {
  components: {
    FavoriteControl,
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
    ...mapGetters([
      "fetchedQuestion",
      "isUserQuestionFavorites",
      "isLoggedIn",
      "fetchedLoginUser"
    ])
  },

  watch: {
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    this.updateCurrentQuestion();
  },

  mounted() {
    if (!this.isLoggedIn) {
      this.$store.commit("DELETE_QUESTION_FAVORITES");
    }
  },

  methods: {
    async updateCurrentQuestion() {
      await this.$store.dispatch(
        "FETCH_QUESTION_WITHOUT_VISITS",
        this.$route.params.id
      );
      this.content = this.fetchedQuestion.content;
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
.question-header {
  display: flex;
  justify-content: space-between;
  padding: 18px 18px 12px 18px;
  border-bottom: solid 1px #e8e8e8;
}

.question-header.mobile {
  flex-direction: column;
  padding: 0 0 12px 0;
}

.left-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.left-container .title {
  font-size: 34px !important;
  font-weight: bold;
}

.left-container .title .mobile {
  font-size: 21px !important;
}

.left-container.mobile {
  order: 2;
}

.right-container {
  min-width: 190px;
  margin-top: 7px;
}

.right-container.mobile {
  order: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0 0 15px 0;
}

.hashtags {
  padding: 12px 0 0 12px;
}

.hashtag {
  margin: 3px 5px;
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

.question-info.mobile {
  margin-bottom: 0;
  justify-content: flex-start;
  padding-left: 4px;
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
  padding: 30px 30px;
}

.inner {
  width: 95%;
  box-sizing: border-box;
  padding: 0;
  border-bottom: solid 1px #e8e8e8;
  min-height: 400px;
}

.favorite {
  display: inline;
}
</style>
