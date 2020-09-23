<template>
  <div class="left-menu" :class="$mq">
    <v-btn
      color="#DAEBEA"
      large
      @click="$router.push(`/questions`)"
      class="menu-btn button"
      :class="$mq"
      >돌아가기
    </v-btn>
    <div class="author-btn" :class="$mq" v-if="isAuthor || isAdmin">
      <v-btn
        color="#DAEBEA"
        large
        @click="$router.push(`/question/edit/${$route.params.id}`)"
        class="menu-btn button"
        :class="$mq"
        >수정하기
      </v-btn>
      <v-btn
        @click="onDeleteQuestion"
        large
        color="#DAEBEA"
        class="menu-btn button delete-btn"
        :class="$mq"
        >삭제하기
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isAuthor", "isAdmin"],

  methods: {
    async onDeleteQuestion() {
      if (confirm("정말 삭제하시겠습니까?")) {
        const questionId = this.$route.params.id;
        await this.$store.dispatch("DELETE_QUESTION", questionId);
        await this.$router.push(`/questions`);
      }
    }
  }
};
</script>

<style scoped>
.left-menu {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 200px;
}

.left-menu.mobile {
  flex-direction: row;
  justify-content: center;
  margin-top: 20px;
}

.menu-btn {
  margin-top: 30px;
}

.menu-btn.mobile {
  margin: 0 5px;
  width: 70px !important;
  height: 35px !important;
}

.author-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.author-btn.mobile {
  flex-direction: row;
}

.delete-btn:hover {
  background-color: #fc9d9a !important;
}
</style>
