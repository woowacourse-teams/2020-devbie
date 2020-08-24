<template>
  <div class="notice-list">
    <div>
      <v-card elevation="16" max-width="400" class="mx-auto">
        <v-card-title class="white--text blue darken-4">
          <v-btn icon @click="$router.push(`/notices`)" class="back-button">
            <v-icon color="white" large>
              mdi-arrow-left-bold-circle-outline
            </v-icon>
          </v-btn>
          <div class="card-title">
            전체공고
          </div>
        </v-card-title>
        <v-divider></v-divider>

        <v-virtual-scroll
          :bench="benched"
          :items="items"
          height="500"
          item-height="64"
        >
          <template v-slot="{ item }">
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
            <v-divider></v-divider>
          </template>
        </v-virtual-scroll>
      </v-card>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      benched: 0
    };
  },
  created() {
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
  computed: {
    ...mapGetters([
      "fetchedNotices",
      "fetchedNoticeType",
      "fetchedJobPosition",
      "fetchedLanguage"
    ]),
    items() {
      return this.fetchedNotices;
    }
  }
};
</script>

<style scoped>
.card-title {
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
  opacity: 0.7;
}
</style>
