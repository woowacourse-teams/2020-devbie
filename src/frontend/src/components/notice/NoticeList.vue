<template>
  <div>
    <template v-if="isSearch()">
      <h2 class="search-message">'{{ keyword }}'로 검색한 결과입니다.</h2>
    </template>
    <v-row dense v-scroll="onScroll" class="list-parent" :class="$mq">
      <div v-for="notice in notices" :key="notice.id" class="item" :class="$mq">
        <v-card>
          <v-img
            @click="
              $router.push(`/notices/${noticeType || 'JOB'}/${notice.id}`)
            "
            :src="notice.image"
            class="white--text align-end card-image"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title-text"
              v-html="addHighlight(sliceText(notice.name, 20))"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <v-col cols="12">
              <div
                align="left"
                class="big-font notice-title"
                style="font-weight: bold"
              >
                <p v-html="addHighlight(sliceText(notice.title, 40))"></p>
              </div>
              <div class="medium-font">
                언어 : {{ notice.languages.join(", ") }}
              </div>
              <div class="medium-font">포지션 : {{ notice.jobPosition }}</div>
              <favorite-control
                class="heart-icon"
                :targetObjectId="notice.id"
                :isUserFavorite="isUserNoticeFavorites(notice.id)"
                :isQuestion="false"
              ></favorite-control>
            </v-col>
          </v-card-actions>
        </v-card>
      </div>
      <v-progress-circular
        v-if="isBottom"
        :size="50"
        color="primary"
        indeterminate
        class="loading-progress last-item"
      ></v-progress-circular>
      <div v-if="isEndPage()" class="last-item">
        모든 공고를 조회하셨습니다.
      </div>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import FavoriteControl from "../favorite/FavoriteControl";
import { getAction } from "@/api";
import { createNoticeObj } from "@/utils/noticeUtil";

export default {
  components: { FavoriteControl },

  props: ["noticeType", "jobPosition", "language", "keyword"],

  data() {
    return {
      notices: [],
      isBottom: false,
      isReady: true,
      page: 1,
      lastPage: 1
    };
  },

  computed: {
    ...mapGetters([
      "isLoggedIn",
      "fetchedLoginUser",
      "isUserNoticeFavorites",
      "fetchedNoticeFavorites"
    ])
  },

  watch: {
    noticeType() {
      this.initNotices();
    },
    jobPosition() {
      this.initNotices();
    },
    language() {
      this.initNotices();
    },
    keyword() {
      this.initNotices();
    }
  },

  async created() {
    if (this.isLoggedIn) {
      await this.initFavoriteState();
    }
    await this.initNotices();
  },

  methods: {
    async onScroll({ target }) {
      const { scrollTop, clientHeight, scrollHeight } = target.scrollingElement;
      let clientCurrentHeight = scrollTop + clientHeight;
      let componentHeight = scrollHeight - this.$el.lastElementChild.offsetTop;
      const currentState = clientCurrentHeight > componentHeight;

      if (this.isBottom !== currentState && this.page <= this.lastPage) {
        if (!this.isReady) {
          return;
        }
        this.isReady = false;

        this.isBottom = true;
        await this.addNotices();
        this.isBottom = false;
      }
    },

    isEndPage() {
      return this.page > this.lastPage;
    },

    initNotices() {
      if (!this.isReady) {
        return;
      }
      this.isReady = false;

      this.notices = [];
      this.page = 1;
      this.addNotices();
    },

    async addNotices() {
      const param = createNoticeObj(
        this.noticeType,
        this.keyword,
        this.language,
        this.jobPosition
      );
      param["page"] = this.page;

      const queryParam = new URLSearchParams(param).toString();

      try {
        const { data } = await getAction(`/api/notices?` + queryParam);
        this.lastPage = data["lastPage"];
        this.notices = this.notices.concat(data["noticeResponses"]);
      } catch (error) {
        await this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "공고를 불러오지 못했습니다."
        );
      }

      this.page = this.page + 1;
      this.isReady = true;
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
      if (!this.isLoggedIn) {
        this.$store.commit("DELETE_NOTICE_FAVORITES");
        return;
      }
      await this.$store.dispatch("FETCH_LOGIN_USER");
      await this.$store.dispatch("FETCH_MY_FAVORITES", {
        userId: this.fetchedLoginUser.id,
        object: "notice"
      });
    },

    sliceText(text, maximumLength) {
      if (text.length > maximumLength) {
        return text.substr(0, maximumLength) + "...";
      }
      return text;
    },

    addHighlight(text) {
      if (this.keyword === undefined || this.keyword === "") {
        return text;
      }

      const regex = new RegExp(this.keyword, "g");
      const highlightingText = text.replace(
        regex,
        `<span style="background: #f1c40f">` + this.keyword + "</span>"
      );

      return highlightingText;
    },

    isSearch() {
      if (this.keyword === undefined) {
        return false;
      }
      return this.keyword.trim() !== "";
    }
  }
};
</script>

<style scoped>
.v-card {
  height: 100%;
}

.big-font {
  font-size: 17px;
}

.medium-font {
  font-size: 13px;
}

.item:last-child {
  margin-right: auto;
}

.item {
  width: 22%;
  margin: 0 30px 50px 0;
}

.card-image {
  width: 100%;
  min-width: 200px;
}

.card-image:hover {
  opacity: 0.6;
}

.card-title-text {
  justify-content: center;
  color: white;
  max-height: 40px;
  background-color: rgba(0, 0, 0, 0.6);
  flex-wrap: nowrap;
}

.notice-title {
  font-weight: bold;
  padding-bottom: 10px;
}

.heart-icon {
  position: absolute;
  right: 15px;
  bottom: 15px;
}

.loading-progress {
  text-align: center;
  left: 50%;
}

.last-item {
  flex-basis: 100%;
  height: 100px;
}

.list-parent.mobile {
  width: 80%;
  margin: auto;
}

.item.mobile {
  width: 100%;
  /*margin-right: 100px;*/
  margin: 0 0 50px 0;
}

.search-message {
  text-align: center;
  margin-bottom: 50px;
}
</style>
