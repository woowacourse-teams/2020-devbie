<template>
  <div class="notice-list" :class="$mq">
    <div class="filters">
      <notice-type></notice-type>
      <notice-filters></notice-filters>
      <notice-search></notice-search>
    </div>
    <notice-list></notice-list>
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
  align-content: center;
  justify-content: center;
  margin-bottom: 50px;
}

.filters > * {
  margin-right: 120px;
}
</style>
