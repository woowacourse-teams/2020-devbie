<template>
  <div>
    <v-row dense>
      <v-col
        v-for="notice in fetchedNotices"
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
      "fetchedNotices",
      "fetchedNoticeType",
      "fetchedJobPosition",
      "fetchedLanguage",
      "isUserNoticeFavorites",
      "fetchedNoticeFavorites"
    ])
  },

  watch: {
    fetchedNoticeType() {
      this.getNotices();
    },
    fetchedJobPosition() {
      this.getNotices();
    },
    fetchedLanguage() {
      this.getNotices();
    },
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    this.getNotices();

    if (this.isLoggedIn) {
      this.initFavoriteState();
    }
  },

  methods: {
    getNotices() {
      const param = {
        noticeType: this.fetchedNoticeType,
        jobPosition: this.fetchedJobPosition,
        language: this.fetchedLanguage
      };
      const queryParam = new URLSearchParams(param).toString();
      try {
        this.$store.dispatch("FETCH_NOTICES", queryParam);
      } catch (error) {
        console.log("공고 리스트 불러오기 실패 " + error.response.data.message);
        this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "공고를 불러오지 못했습니다."
        );
      }
    },
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
.selector-item {
  margin: 0 30px 50px 0;
}

.card-title {
  justify-content: center;
}

.notice-info {
  display: flex;
  justify-content: space-between;
}

.notice-info :first-child {
  margin-left: 10px;
}

.notice-info :last-child {
  margin-right: 10px;
}
.v-card:hover {
  opacity: 0.6;
}

.clicked {
  background-color: pink !important;
}
</style>
