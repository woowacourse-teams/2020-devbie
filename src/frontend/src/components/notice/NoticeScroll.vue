<template>
  <div class="notice-list">
    <div>
      <v-card elevation="16" max-width="400" class="mx-auto">
        <v-card-title class="darken-4 card-title">
          <v-btn icon @click="$router.push(`/notices`)" class="back-button">
            <v-icon large>
              mdi-arrow-left-bold-circle-outline
            </v-icon>
          </v-btn>
          <div class="card-title-text">
            전체공고
          </div>
        </v-card-title>
        <v-divider></v-divider>

        <div
          style="overflow-y: scroll; height:500px;"
          class="scroll-content"
          @scroll="onScroll"
        >
          <template v-for="item in items">
            <v-list-item :key="item.id">
              <v-list-item-avatar>
                <v-img :src="item.image" height="50" max-width="50"></v-img>
              </v-list-item-avatar>
              <v-list-item-content @click="$router.push(`/notices/${item.id}`)">
                <v-list-item-title style="font-weight: bold">
                  {{ item.name }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  {{ item.title }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
            <v-divider :key="item.id"></v-divider>
          </template>
        </div>
      </v-card>
    </div>
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
      "fetchedKeyword",
      "fetchedPage",
      "fetchedLastPage"
    ]),
    items() {
      return this.fetchedNotices;
    }
  },

  created() {
    if (this.fetchedNotices.length !== 0) {
      return;
    }
    const param = {
      noticeType: this.fetchedNoticeType,
      jobPosition: this.fetchedJobPosition,
      language: this.fetchedLanguage
    };
    const queryParam = new URLSearchParams(param).toString();
    try {
      this.$store.dispatch("FETCH_NOTICES", queryParam);
    } catch (error) {
      console.log("공고 리스트 불러오기 실패 : " + error.response.data.message);
      this.$store.dispatch(
        "UPDATE_SNACKBAR_TEXT",
        "공고를 불러오지 못했습니다."
      );
    }
  },

  methods: {
    async onScroll({ target }) {
      if (
        target.scrollTop + target.clientHeight >= target.scrollHeight &&
        this.fetchedPage <= this.fetchedLastPage
      ) {
        this.isBottom = true;
        await this.addNotices();
        this.isBottom = false;
      }
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
      await this.$store.dispatch("FETCH_NOTICES", queryParam);
    }
  }
};
</script>

<style scoped>
.card-title-text {
  margin: auto;
}
.back-button {
  position: absolute;
}
.notice-list {
  width: 400px;
  flex-basis: auto;
  margin: 50px 100px 100px 0px;
}
.v-list-item:hover {
  opacity: 0.6;
  cursor: pointer;
}
.card-title {
  background-color: #daebea;
  font-weight: bold;
}
</style>
