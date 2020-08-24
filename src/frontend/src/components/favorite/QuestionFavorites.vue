<template>
  <div>
    <ul class="question-list">
      <li
        v-for="question in fetchedQuestionFavorites"
        v-bind:key="question.id"
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
        <favorite-control
          :targetObjectId="question.id"
          :isUserFavorite="isUserQuestionFavorites(question.id)"
          :isQuestion="true"
        ></favorite-control>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import FavoriteControl from "./FavoriteControl";

export default {
  components: { FavoriteControl },

  computed: {
    ...mapGetters([
      "isLoggedIn",
      "fetchedLoginUser",
      "fetchedQuestionFavorites",
      "isUserQuestionFavorites"
    ])
  },

  watch: {
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    if (this.isLoggedIn) {
      this.initFavoriteState();
    }
  },

  methods: {
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
</style>
