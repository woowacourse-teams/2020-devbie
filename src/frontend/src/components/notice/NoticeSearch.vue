<template>
  <div>
    <v-text-field
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
      keyword: "",
      previous: "pre"
    };
  },

  methods: {
    onSearch() {
      if (this.keyword === this.previous) {
        return;
      }
      this.previous = this.keyword;
      this.$store.commit("SET_KEYWORD", this.keyword);
    }
  }
};
</script>

<style scoped>
.search-bar .input {
  padding: 0;
}
</style>
