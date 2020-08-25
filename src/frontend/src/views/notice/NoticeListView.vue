<template>
  <div class="notice-list" style="margin: 50px 150px 50px 150px">
    <div class="filters">
      <notice-type></notice-type>
      <notice-filters></notice-filters>
      <notice-search></notice-search>
      <i class="fas fa-search"></i>
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
