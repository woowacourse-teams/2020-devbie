<template>
  <div>
    <v-btn icon @click.stop="onFavorite(targetObjectId)">
      <v-icon color="red" v-if="isUserFavorite">mdi-heart</v-icon>
      <v-icon v-else>mdi-heart</v-icon>
    </v-btn>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  props: ["isUserFavorite", "targetObjectId", "isQuestion"],

  computed: {
    ...mapGetters(["isLoggedIn", "fetchedLoginUser"])
  },

  watch: {
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    console.log("FavoriteControl : " + this.isLoggedIn);
    if (this.isLoggedIn) {
      this.initFavoriteState();
    }
  },

  methods: {
    onFavorite(objectId) {
      if (!this.isLoggedIn) {
        console.log("you should login");
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인이 필요합니다.");
        return;
      }
      if (this.isUserFavorite) {
        try {
          this.$store.dispatch("DELETE_FAVORITE", {
            objectId,
            object: this.isQuestion ? "question" : "notice"
          });
        } catch (error) {
          console.error("즐겨찾기 삭제 실패" + error.response.data.message);
          this.$store.disabled(
            "UPDATE_SNACKBAR_TEXT",
            "즐겨찾기 삭제에 실패했습니다."
          );
        }
      } else {
        const param = {
          objectId,
          objectType: this.isQuestion ? "question" : "notice"
        };
        const queryParam = new URLSearchParams(param).toString();
        try {
          this.$store.dispatch("CREATE_FAVORITE", {
            queryParam,
            object: this.isQuestion ? "question" : "notice"
          });
        } catch (error) {
          console.error("즐겨찾기 추가 실패" + error.response.data.message);
          this.$store.disabled(
            "UPDATE_SNACKBAR_TEXT",
            "즐겨찾기 추가에 실패했습니다."
          );
        }
      }
      this.initFavoriteState();
    },
    async initFavoriteState() {
      await this.$store.dispatch("FETCH_LOGIN_USER");
      await this.$store.dispatch("FETCH_MY_FAVORITES", {
        userId: this.fetchedLoginUser.id,
        object: this.isQuestion ? "question" : "notice"
      });
    }
  }
};
</script>

<style scoped></style>
