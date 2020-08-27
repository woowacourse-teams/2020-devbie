<template>
  <div class="interview" :class="$mq">
    <question-filters id="question-filters"></question-filters>
    <question-list
      :orderBy="orderBy"
      :title="title"
      :content="content"
      :hashtag="hashtag"
      id="question-list"
    ></question-list>
    <question-control id="question-control"></question-control>
  </div>
</template>

<script>
import QuestionFilters from "../../components/question/QuestionFilters";
import QuestionList from "../../components/question/QuestionList";
import QuestionControl from "../../components/question/QuestionControl";
import { mapGetters } from "vuex";

export default {
  components: {
    QuestionFilters,
    QuestionList,
    QuestionControl
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
  width: 95%;
  margin: 0 auto;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

#question-filters {
  flex-grow: 1;
  min-width: 160px;
}

#question-list {
  flex-grow: 10;
}

#question-control {
  flex-grow: 1;
  margin-top: 25px;
  display: flex;
  justify-content: center;
  text-decoration: none;
}
</style>
