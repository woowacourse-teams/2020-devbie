<template>
  <div class="question-control">
    <hashtag-list></hashtag-list>
    <v-btn
      large
      class="control-btn question-create button"
      @click="onCreateForm"
      color="#DAEBEA"
      >질문 하기
    </v-btn>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import HashtagList from "../../components/hashtag/HashtagList";

export default {
  computed: {
    ...mapGetters(["isLoggedIn"])
  },
  components: {
    HashtagList
  },
  methods: {
    onCreateForm() {
      if (!this.isLoggedIn) {
        console.log("you should login");
        return;
      }
      this.$router.push("/question/create");
    }
  }
};
</script>

<style scoped>
.question-control {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-width: 200px;
}

.control-btn {
  width: 95px;
  margin-top: 20px;
}
</style>
