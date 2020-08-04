<template>
  <div>
    <v-row dense>
      <v-col
        v-for="notice in fetchedNotices.noticeResponses"
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
              v-text="notice.name + `- 공고 타이틀`"
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
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    getNotices() {
      const url = {
        noticeType: this.fetchedNoticeType,
        jobPosition: this.fetchedJobPosition,
        language: this.fetchedLanguage
      };
      const queryUrl = new URLSearchParams(url).toString();
      this.$store.dispatch("FETCH_NOTICES", queryUrl);
    }
  },
  computed: {
    ...mapGetters(["fetchedNoticeType"]),
    ...mapGetters(["fetchedJobPosition"]),
    ...mapGetters(["fetchedLanguage"]),
    ...mapGetters(["fetchedNotices"])
  },
  created() {
    this.getNotices();
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
</style>
