<template>
  <div class="notice-list">
    <div>
      <v-card elevation="16" max-width="400" class="mx-auto">
        <v-card-title class="white--text blue darken-4">
          전체 공고

          <v-spacer></v-spacer>

          <v-btn
            @click="$router.push(`/notices`)"
            color="white"
            class="text--primary"
            fab
            small
          >
            <v-icon>mdi-format-list-bulleted</v-icon>
          </v-btn>
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
              <v-list-item-action>
                <v-btn fab small depressed color="primary">
                  {{ item.id }}
                </v-btn>
              </v-list-item-action>

              <v-list-item-content @click="$router.push(`/notices/${item.id}`)">
                <v-list-item-title>
                  {{ item.name }} 채용 공고 {{ item.title }}
                </v-list-item-title>
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
      return this.fetchedNotices.noticeResponses;
    }
  }
};
</script>

<style scoped>
.notice-list {
  width: 400px;
  flex-basis: auto;
  margin-right: 200px;
}
.v-list-item:hover {
  opacity: 0.7;
}
</style>
