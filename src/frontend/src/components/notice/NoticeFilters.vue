<template>
  <div class="filter-box">
    <br />
    <v-select
      class="filters"
      v-model="selectedPosition"
      v-on:change="onChange"
      item-text="text"
      item-value="key"
      :items="fetchedJobPositions"
      hide-details
      label="직군"
      menu-props="auto"
      single-line
    ></v-select>
    <v-select
      class="filters"
      v-model="selectedLanguage"
      v-on:change="onChange"
      item-text="text"
      item-value="key"
      :items="fetchedLanguages"
      menu-props="auto"
      label="언어"
      hide-details
      single-line
    ></v-select>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { createNoticeUrl } from "@/utils/noticeUtil";

export default {
  props: ["jobPosition", "language"],

  data() {
    return {
      selectedPosition: this.jobPosition,
      selectedLanguage: this.language,
      currentNoticeType: this.$route.query.noticeType,
      currentKeyword: this.$route.query.keyword
    };
  },

  computed: {
    ...mapGetters(["fetchedLanguages", "fetchedJobPositions"])
  },

  watch: {
    jobPosition() {
      this.setFilters();
    },
    language() {
      this.setFilters();
    }
  },

  created() {
    this.$store.dispatch("FETCH_FILTERS");
  },

  methods: {
    setFilters() {
      (this.selectedPosition = this.$route.query.jobPosition),
        (this.selectedLanguage = this.$route.query.language);
    },

    async onChange() {
      const noticeUrl = await createNoticeUrl(
        this.currentNoticeType,
        this.currentKeyword,
        this.selectedLanguage,
        this.selectedPosition
      );
      await this.$router.push(noticeUrl);
    }
  }
};
</script>

<style scoped>
.filter-box {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Noto Sans KR", "Noto Sans JP", sans-serif;
}

.filters {
  padding: 0;
  margin: 0 50px 0 0;
  width: 12em;
  max-width: 12em;
}
</style>
