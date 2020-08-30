<template>
  <div class="controller" :class="$mq">
    <hashtag-list></hashtag-list>
    <v-btn
      large
      class="control-btn question-create button"
      @click="onCreateForm"
      color="#DAEBEA"
      :class="$mq"
      >질문 하기
    </v-btn>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import HashtagList from "../../components/hashtag/HashtagList";

export default {
  components: {
    HashtagList
  },

  computed: {
    ...mapGetters(["isLoggedIn"])
  },

  methods: {
    onCreateForm() {
      if (!this.isLoggedIn) {
        console.log("you should login");
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인이 필요합니다.");
        return;
      }
      this.$router.push("/question/create");
    }
  }
};
</script>

<style scoped>
.controller {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-width: 200px;
}

.controller.mobile {
  margin-top: 5px;
}

.control-btn {
  width: 95px;
  margin-top: 25px;
}

.control-btn.mobile {
  margin: 0 5px;
  width: 60px !important;
  height: 40px !important;
  font-size: 10px !important;
}

.control-btn.mobile {
  margin-top: 0;
}
</style>
