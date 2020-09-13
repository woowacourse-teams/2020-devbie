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
          <template v-for="item in notices">
            <v-list-item :key="item.id">
              <v-list-item-avatar>
                <v-img :src="item.image" height="50" max-width="50"></v-img>
              </v-list-item-avatar>
              <v-list-item-content
                @click="$router.push(`/notices/${noticeType}/${item.id}`)"
              >
                <v-list-item-title style="font-weight: bold">
                  {{ item.name }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  {{ item.title }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
            <v-divider :key="`divider` + item.id"></v-divider>
          </template>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import { createNoticeObj } from "@/utils/noticeUtil";
import { getAction } from "@/api";

export default {
  data() {
    return {
      notices: [],
      isBottom: false,
      isReady: true,
      page: 1,
      lastPage: 1000,
      noticeType: this.$route.path.split("/")[2]
    };
  },

  watch: {
    noticeType() {
      this.initNotices();
    }
  },

  async created() {
    if (this.notices.length > 0) {
      return;
    }
    await this.addNotices();
  },

  methods: {
    async onScroll({ target }) {
      if (
        target.scrollTop + target.clientHeight >= target.scrollHeight &&
        this.page <= this.lastPage
      ) {
        if (!this.isReady) {
          return;
        }
        this.isReady = false;

        this.isBottom = true;
        await this.addNotices();
        this.isBottom = false;
      }
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
  min-width: 100px;
  width: 30%;
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
