<template>
  <div class="notice-list" :class="$mq">
    <div class="filters" :class="$mq">
      <notice-type :noticeType="noticeType" class="item"></notice-type>
      <notice-filters
        :jobPosition="jobPosition"
        :language="language"
        class="item"
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
  margin: 50px 150px 50px 150px;
}

.notice-list.mobile {
  margin: 0 0 0 0;
}

.filters {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: center;
  margin-bottom: 50px;
}

.item {
  margin-right: 10%;
  margin-bottom: 10px;
}

.filters.mobile {
  flex-wrap: wrap;
}
</style>
