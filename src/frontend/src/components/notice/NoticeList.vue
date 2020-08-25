<template>
  <div>
    <v-row dense v-scroll="onScroll">
      <v-col
        v-for="notice in fetchedNotices"
        :key="notice.id"
        :cols="2"
        class="selector-item"
      >
        <v-card @click.stop="$router.push(`/notices/${notice.id}`)">
          <v-img
            :src="
              notice.image !== ''
                ? notice.image
                : 'https://cdn.vuetifyjs.com/images/cards/house.jpg'
            "
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
            <favorite-control
              :targetObjectId="notice.id"
              :isUserFavorite="isUserNoticeFavorites(notice.id)"
              :isQuestion="false"
            ></favorite-control>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
    <v-progress-circular
      v-if="isBottom"
      :size="50"
      color="primary"
      indeterminate
      class="loading-progress"
    ></v-progress-circular>
    <template v-if="isEndPage()">
      모든 공고를 조회하셨습니다.
    </template>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import FavoriteControl from "../favorite/FavoriteControl";

export default {
  components: { FavoriteControl },

  data() {
    return {
      isBottom: false
    };
  },

  computed: {
    ...mapGetters([
      "fetchedNotices",
      "fetchedNoticeType",
      "fetchedJobPosition",
      "fetchedLanguage",
      "fetchedKeyword",
      "fetchedPage",
      "fetchedLastPage",
      "isLoggedIn",
      "fetchedLoginUser",
      "isUserNoticeFavorites",
      "fetchedNoticeFavorites"
    ])
  },

  watch: {
    fetchedNoticeType() {
      this.addNotices();
    },
    fetchedJobPosition() {
      this.addNotices();
    },
    fetchedLanguage() {
      this.addNotices();
    },
    fetchedKeyword() {
      this.addNotices();
    },
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    if (this.fetchedNotices.length > 0) {
      return;
    }
    this.addNotices();

    if (this.isLoggedIn) {
      this.initFavoriteState();
    }
  },

  methods: {
    async onScroll({ target }) {
      const { scrollTop, clientHeight, scrollHeight } = target.scrollingElement;
      let clientCurrentHeight = scrollTop + clientHeight;
      let componentHeight = scrollHeight - this.$el.lastElementChild.offsetTop;
      const currentState = clientCurrentHeight > componentHeight;

      if (
        this.isBottom !== currentState &&
        this.fetchedPage <= this.fetchedLastPage
      ) {
        this.isBottom = true;
        await this.addNotices();
        this.isBottom = false;
      }
    },

    isEndPage() {
      return this.fetchedPage > this.fetchedLastPage;
    },

    async addNotices() {
      const param = {
        noticeType: this.fetchedNoticeType,
        jobPosition: this.fetchedJobPosition,
        language: this.fetchedLanguage,
        page: this.fetchedPage,
        keyword: this.fetchedKeyword
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
      await this.$store.dispatch("FETCH_MY_FAVORITES", {
        userId: this.fetchedLoginUser.id,
        object: "notice"
      });
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
.loading-progress {
  text-align: center;
  left: 50%;
}
</style>
