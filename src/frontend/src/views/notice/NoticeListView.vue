<template>
  <div style="margin: 50px 150px 50px 150px">
    <div class="filters">
      <notice-type></notice-type>
      <notice-filters></notice-filters>
      <v-text-field class="search-bar"></v-text-field>
    </div>
    <notice-cards></notice-cards>
  </div>
</template>

<script>
import NoticeType from "../../components/notice/NoticeType";
import NoticeFilters from "../../components/notice/NoticeFilters";
import NoticeCards from "../../components/notice/NoticeList";

export default {
  components: {
    NoticeType,
    NoticeFilters,
    NoticeCards
  },
  data() {
    return {};
  }
};
</script>

<style scoped>
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
