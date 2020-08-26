<template>
  <div class="select-box">
    <v-btn
      name="NOTICE_FAVORITE"
      class="select-btn"
      :class="{ 'button-active': isNoticeFavorite }"
      @click="changeFavoriteType"
    >
      공고
    </v-btn>
    <v-btn
      name="QUESTION_FAVORITE"
      class="select-btn"
      :class="{ 'button-active': isQuestionFavorite }"
      @click="changeFavoriteType"
      >면접 질문</v-btn
    >
  </div>
</template>

<script>
export default {
  props: ["isNoticeFavorite", "isQuestionFavorite"],

  methods: {
    async changeFavoriteType(event) {
      const favoriteType = event.currentTarget.name;
      if (favoriteType === "NOTICE_FAVORITE") {
        await this.$router.push("/favorites/notice");
      } else {
        await this.$router.push("/favorites/question");
      }
    }
  }
};
</script>

<style scoped>
.select-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.select-btn {
  margin-right: 15px;
  padding: 5px;
}

.select-btn:hover {
  opacity: 0.8;
}

.button-active {
  background-color: rgb(135, 189, 214) !important;
}
</style>
