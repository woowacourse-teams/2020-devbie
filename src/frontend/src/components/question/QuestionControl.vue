<template>
  <div class="question-control">
    <v-btn
      class="control-btn hashtag-list"
      @click="$router.push('/hashtags')"
      color="#DAEBEA"
      >태그 목록</v-btn
    >
    <v-btn
      v-if="isLoggedIn"
      class="control-btn question-create"
      @click="$router.push('/create-question')"
      color="#DAEBEA"
      >질문 하기
    </v-btn>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["isLoggedIn"])
  }
};
</script>

<style scoped>
.question-control {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.control-btn {
  width: 95px;
  margin-bottom: 25px;
}
</style>
