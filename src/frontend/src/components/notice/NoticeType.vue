<template>
  <div class="select-box" :class="$mq">
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

  methods: {
    async changeNoticeType(e) {
      if (e.currentTarget.name === this.fetchedNoticeType) {
        return;
      }
      const url = createNoticeUrl(e.currentTarget.name);
      await this.$router.push(url);
    },
    isJob() {
      return this.noticeType === undefined || this.noticeType === "JOB";
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

.select-box.mobile {
  margin-top: 30px;
  margin-right: 0;
}
</style>
