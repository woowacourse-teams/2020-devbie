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
export default {
  data() {
    return {
      keyword: ""
    };
  },

  methods: {
    onSearch() {
      if (this.keyword.trim() === "") {
        return;
      }
      this.$store.commit("SET_KEYWORD", this.keyword);
      this.keyword = "";
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
