<template>
  <div>
    <v-row dense>
      <v-col
        v-for="notice in fetchedNoticeFavorites"
        :key="notice.id"
        :cols="2"
        class="selector-item"
      >
        <v-card @click.stop="$router.push(`/notices/${notice.id}`)">
          <v-img
            :src="notice.image"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title"
              v-text="`${notice.name} - ${notice.title}`"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <div>
              {{ notice.languages.join(", ") }}
            </div>
            <div>
              {{ notice.jobPosition }}
            </div>
            <v-btn
              icon
              @click.stop="onFavorite(notice.id)"
              :class="{ clicked: isUserNoticeFavorites(notice.id) }"
            >
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters([
      "isLoggedIn",
      "fetchedLoginUser",
      "isUserNoticeFavorites",
      "fetchedNoticeFavorites"
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
    onFavorite(noticeId) {
      if (!this.isLoggedIn) {
        console.log("you should login");
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인이 필요합니다.");
        return;
      }
      if (this.isUserNoticeFavorites(noticeId)) {
        try {
          this.$store.dispatch("DELETE_FAVORITE", noticeId);
        } catch (error) {
          console.error("즐겨찾기 삭제 실패" + error.response.data.message);
          this.$store.disabled(
            "UPDATE_SNACKBAR_TEXT",
            "즐겨찾기 삭제에 실패했습니다."
          );
        }
      } else {
        const param = {
          objectType: "notice",
          objectId: noticeId
        };
        const queryParam = new URLSearchParams(param).toString();
        try {
          this.$store.dispatch("CREATE_FAVORITE", queryParam);
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
      await this.$store.dispatch(
        "FETCH_MY_NOTICE_FAVORITES",
        this.fetchedLoginUser.id
      );
    }
  }
};
</script>

<style scoped>
.clicked {
  background-color: pink;
}
</style>
