<template>
  <div class="filter-box" :class="$mq">
    <br />
    <v-select
      class="filters"
      :class="$mq"
      v-model="selectedPosition"
      v-on:change="onChange"
      item-text="text"
      item-value="key"
      :items="fetchedFilterByJobPositions"
      hide-details
      label="직군"
      menu-props="auto"
      single-line
    ></v-select>
    <v-select
      class="filters"
      :class="$mq"
      v-model="selectedLanguage"
      v-on:change="onChange"
      item-text="text"
      item-value="key"
      :items="fetchedFilterByLanguages"
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
      selectedLanguage: this.language
    };
  },

  computed: {
    ...mapGetters(["fetchedFilterByJobPositions", "fetchedFilterByLanguages"])
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
      this.selectedPosition = this.$route.query.jobPosition;
      this.selectedLanguage = this.$route.query.language;
    },

    async onChange() {
      const noticeUrl = await createNoticeUrl(
        this.$route.query.noticeType,
        this.$route.query.keyword,
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
  width: 10em;
  max-width: 12em;
  margin-right: 50px;
}

.filters:last-child {
  margin-right: 0;
}

.filter-box.mobile {
  margin-top: 30px;
  justify-content: center;
  margin-right: 0;
}

.filters.mobile {
  max-width: 100px;
  margin: 0 10px 15px 0;
}
</style>
