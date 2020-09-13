<template>
  <div class="select-box">
    <v-btn
      large
      name="JOB"
      class="select-btn"
      :class="{ 'button-active': isJob() }"
      @click="changeNoticeType"
      >채용
    </v-btn>
    <v-btn
      large
      name="EDUCATION"
      class="select-btn"
      :class="{ 'button-active': isEducation() }"
      @click="changeNoticeType"
      >교육
    </v-btn>
  </div>
</template>

<script>
import { createNoticeUrl } from "@/utils/noticeUtil";

export default {
  props: ["noticeType"],

  data() {
    return {
      jobPosition: this.$route.query.jobPosition,
      language: this.$route.query.language,
      keyword: this.$route.query.keyword
    };
  },

  methods: {
    async changeNoticeType(e) {
      if (e.currentTarget.name === this.fetchedNoticeType) {
        return;
      }
      await this.$store.commit("SET_NOTICE_TYPE", e.currentTarget.name);

      const url = createNoticeUrl(e.currentTarget.name);
      await this.$router.push(url);
    },
    isJob() {
      return this.noticeType === "JOB";
    },
    isEducation() {
      return this.noticeType === "EDUCATION";
    }
  }
};
</script>

<style scoped>
.select-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.select-btn {
  margin-right: 15px;
  font-size: 16px;
  width: 95px;
  padding: 5px;
  color: #35495e !important;
}

.select-btn:hover {
  opacity: 0.8;
}

.button-active {
  background-color: rgb(218, 235, 234) !important;
}
</style>
