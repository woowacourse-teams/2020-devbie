<template>
  <div>
    <v-text-field
      append-icon="fas fa-search"
      style="display: inline-block"
      class="search-bar"
      v-model="keyword"
      @keyup.enter="onSearch"
      placeholder="검색 후 엔터를 누르세요!"
    ></v-text-field>
  </div>
</template>

<script>
import { createNoticeUrl } from "@/utils/noticeUtil";

export default {
  data() {
    return {
      keyword: this.$route.query.keyword
    };
  },

  methods: {
    async onSearch() {
      if (this.keyword.trim() === "") {
        return;
      }
      const noticeUrl = await createNoticeUrl(
        this.$route.query.noticeType,
        this.keyword,
        this.$route.query.language,
        this.$route.query.jobPosition
      );

      this.keyword = "";
      await this.$router.push(noticeUrl);
    }
  }
};
</script>

<style scoped>
.search-bar .input {
  padding: 0;
}

.search-bar {
  width: 13em;
}
</style>
