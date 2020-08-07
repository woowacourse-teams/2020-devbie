<template>
  <div class="detail">
    <div class="left-menu">
      <router-link :to="`/questions`">
        <v-btn color="#DAEBEA" class="menu-btn">돌아가기</v-btn>
      </router-link>
      <div class="author-btn" v-if="author">
        <router-link :to="`/edit-question/${this.$route.params.id}`">
          <v-btn color="#DAEBEA" class="menu-btn">수정하기</v-btn>
        </router-link>
        <v-btn @click="onDeleteQuestion" color="#E8E8E8" class="menu-btn"
          >삭제하기
        </v-btn>
      </div>
    </div>
    <div class="question-box">
      <question-detail
        @fetchUserId="isAuthor"
        id="question-detail"
      ></question-detail>
      <answer-list></answer-list>
    </div>
    <v-snackbar v-model="snackbar" :multi-line="true" top>
      {{ snackbarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="snackbar = false">
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import QuestionDetail from "../../components/question/QuestionDetail";
import AnswerList from "../../components/question/AnswerList";

export default {
  components: {
    QuestionDetail,
    AnswerList
  },
  data() {
    return {
      author: false,
      snackbar: false,
      snackbarText: ""
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser"])
  },
  methods: {
    async onDeleteQuestion() {
      if (confirm("정말 삭제하시겠습니까?")) {
        try {
          const questionId = this.$route.params.id;
          await this.$store.dispatch("DELETE_QUESTION", questionId);
          window.location.href = `/questions`;
        } catch (error) {
          console.log(error);
          if (error.response.status === 401) {
            this.snackbarText = "작성자만 할 수 있습니다.";
          } else {
            this.snackbarText = "삭제 실패했습니다.";
          }
          this.snackbar = true;
        }
      }
    },
    isAuthor(userId) {
      return (this.author = userId === this.fetchedLoginUser.id);
    }
  }
};
</script>

<style scoped>
a {
  text-decoration: none;
}

.detail {
  display: flex;
  justify-content: center;
  margin: 20px 0 auto;
  max-width: 95%;
}

.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 220px;
}
#question-detail {
  margin-bottom: 60px;
}
.menu-btn {
  margin-top: 30px;
}

.author-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.question-box {
  display: flex;
  flex-direction: column;
  flex-grow: 8;
}
</style>
