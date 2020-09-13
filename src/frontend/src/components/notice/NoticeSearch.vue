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
      noticeType: this.$route.query.noticeType,
      keyword: this.$route.query.keyword,
      language: this.$route.query.language,
      jobPosition: this.$route.query.jobPosition
    };
  },

  methods: {
    async onSearch() {
      if (this.keyword.trim() === "") {
        return;
      }
      console.log(this.noticeType);
      const noticeUrl = await createNoticeUrl(
        this.$route.query.noticeType,
        this.keyword,
        this.$route.query.language,
        this.$route.query.jobPosition
      );

      console.log(noticeUrl);

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
  width: 200px;
}
</style>
