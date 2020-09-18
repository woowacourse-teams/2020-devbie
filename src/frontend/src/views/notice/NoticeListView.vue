<template>
  <div class="notice-list" style="margin: 50px 150px 50px 150px">
    <div class="filters">
      <notice-type :noticeType="noticeType"></notice-type>
      <notice-filters
        :jobPosition="jobPosition"
        :language="language"
      ></notice-filters>
      <notice-search></notice-search>
    </div>
    <notice-list
      :noticeType="noticeType"
      :jobPosition="jobPosition"
      :language="language"
      :keyword="keyword"
    ></notice-list>
  </div>
</template>

<script>
import NoticeType from "../../components/notice/NoticeType";
import NoticeFilters from "../../components/notice/NoticeFilters";
import NoticeList from "../../components/notice/NoticeList";
import NoticeSearch from "../../components/notice/NoticeSearch";

export default {
  components: {
    NoticeType,
    NoticeFilters,
    NoticeList,
    NoticeSearch
  },

  props: ["noticeType", "jobPosition", "keyword", "language"],

  data() {
    return {};
  }
};
</script>

<style scoped>
.notice-list {
  font-family: "Jua", sans-serif;
}

.filters {
  display: flex;
  align-content: center;
  justify-content: center;
  margin-bottom: 50px;
}

.filters > * {
  margin-right: 120px;
}
</style>
