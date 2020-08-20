<template>
  <div>
    <v-row dense v-scroll="onScroll">
      <v-col
        v-for="notice in fetchedNotices"
        :key="notice.id"
        :cols="2"
        class="selector-item"
      >
        <v-card @click="$router.push(`/notices/${notice.id}`)">
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
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
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

export default {
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
    }
  },

  created() {
    if (this.fetchedNotices.length > 0) {
      return;
    }
    this.addNotices();
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
      return this.fetchedPage >= this.fetchedLastPage;
    },

    async addNotices() {
      const param = {
        noticeType: this.fetchedNoticeType,
        jobPosition: this.fetchedJobPosition,
        language: this.fetchedLanguage,
        page: this.fetchedPage
      };
      const queryParam = new URLSearchParams(param).toString();
      await this.$store.dispatch("FETCH_NOTICES", queryParam);
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
