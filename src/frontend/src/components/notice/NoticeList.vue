<template>
  <div>
    <v-row dense v-scroll="onScroll">
      <div v-for="notice in fetchedNotices" :key="notice.id" class="item">
        <v-card class="v-card">
          <v-img
            @click="$router.push(`/notices/${notice.id}`)"
            :src="notice.image"
            class="white--text align-end card-image"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title-text"
              v-text="`${notice.name}`"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <v-col cols="12">
              <div
                align="left"
                class="big-font notice-title"
                style="font-weight: bold"
              >
                {{ notice.title }}
              </div>
              <div class="medium-font">
                언어 : {{ notice.languages.join(", ") }}
              </div>
              <div class="medium-font">포지션 : {{ notice.jobPosition }}</div>
              <v-btn icon class="heart-icon">
                <v-icon>mdi-heart</v-icon>
              </v-btn>
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

export default {
  data() {
    return {
      isBottom: false,
      isReady: true
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
      "fetchedLastPage"
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
    fetchedPage() {
      this.isReady = true;
    }
  },

  async created() {
    if (this.fetchedNotices.length > 0) {
      return;
    }
    await this.addNotices();
  },

  methods: {
    async onScroll({ target }) {
      if (!this.isReady) {
        return;
      }

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
      this.isReady = false;

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
    }
  }
};
</script>

<style scoped>
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

.card-title-text {
  justify-content: center;
}
.card-image {
  width: 100%;
}
.card-image:hover {
  opacity: 0.6;
}
.card-title-text {
  color: white;
  max-height: 40px;
  background-color: rgba(0, 0, 0, 0.6);
}

.card-title-text {
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
}
</style>
