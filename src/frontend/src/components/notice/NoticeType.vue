<template>
  <div class="select-box">
    <v-btn
      name="JOB"
      class="select-btn"
      :class="{ 'button-active': isJob() }"
      @click="changeNoticeType"
      >채용</v-btn
    >
    <v-btn
      name="EDUCATION"
      class="select-btn"
      :class="{ 'button-active': isEducation() }"
      @click="changeNoticeType"
      >교육</v-btn
    >
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedNoticeType"])
  },

  methods: {
    async changeNoticeType(e) {
      if (e.currentTarget.name === this.fetchedNoticeType) {
        return;
      }
      await this.$store.commit("SET_NOTICE_TYPE", e.currentTarget.name);
    },
    isJob() {
      return this.fetchedNoticeType === "JOB";
    },
    isEducation() {
      return this.fetchedNoticeType === "EDUCATION";
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
  padding: 5px;
}

.select-btn:hover {
  opacity: 0.8;
}

.button-active {
  background-color: rgb(135, 189, 214) !important;
}
</style>
