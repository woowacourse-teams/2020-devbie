<template>
  <div class="notice-list" style="margin: 50px 150px 50px 150px">
    <div class="filters">
      <notice-type></notice-type>
      <notice-filters></notice-filters>
      <v-text-field class="search-bar"></v-text-field>
    </div>
    <notice-list></notice-list>
  </div>
</template>

<script>
import NoticeType from "../../components/notice/NoticeType";
import NoticeFilters from "../../components/notice/NoticeFilters";
import NoticeList from "../../components/notice/NoticeList";

export default {
  components: {
    NoticeType,
    NoticeFilters,
    NoticeList
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
.search-bar {
  margin: 0;
  padding: 0;
}
.search-bar .input {
  padding: 0;
}
</style>
