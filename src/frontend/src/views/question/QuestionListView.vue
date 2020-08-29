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
import { mapGetters } from "vuex";

export default {
  components: {
    QuestionFilters,
    QuestionControl,
    QuestionList
  },

  props: ["orderBy", "title", "content", "hashtag"],

  computed: {
    ...mapGetters(["fetchedQuestionPage"]),

    compoundKeyword() {
      return [this.title, this.content].join();
    }
  },

  watch: {
    async hashtag() {
      await this.$store.commit("INIT_QUESTIONS");
      if (this.hashtag) {
        await this.addQuestionByHashtag();
      }
    },

    async orderBy() {
      await this.$store.commit("INIT_QUESTIONS");
      if (this.orderBy) {
        await this.addQuestions();
      }
    },

    async compoundKeyword() {
      await this.$store.commit("INIT_QUESTIONS");
      if (this.title || this.content) {
        await this.addQuestions();
      }
    }
  },

  methods: {
    async addQuestions() {
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
    },

    async addQuestionByHashtag() {
      try {
        await this.$store.dispatch("FETCH_QUESTIONS_BY_HASHTAG", this.hashtag);
      } catch (error) {
        console.log("해시태그로 질문 조회 실패");
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
