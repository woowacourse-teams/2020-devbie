<template>
  <div class="search-bar">
    <v-select
      class="filters"
      v-model="selectedScope"
      item-text="name"
      item-value="value"
      :items="scopes"
      hide-details
      menu-props="auto"
      return-object
      single-line
    ></v-select>
    <v-text-field
      v-model="keyword"
      label="질문 검색"
      outlined
      rounded
      dense
      placeholder="제목, 내용으로 검색해보세요"
      @keyup.enter="searchByInput"
      append-icon="fas fa-search"
    >
    </v-text-field>
  </div>
</template>

<script>
export default {
  props: ["searchBy"],

  data() {
    return {
      scopes: [
        { name: "제목", value: "TITLE" },
        { name: "내용", value: "CONTENT" },
        { name: "제목 + 내용", value: "BOTH" }
      ],
      selectedScope: this.$route.query.scope
        ? this.getCurrentScope(this.$route.query.scope)
        : {
            name: "제목",
            value: "TITLE"
          },
      keyword: ""
    };
  },

  created() {
    this.keyword = this.searchBy;
  },

  methods: {
    searchByInput() {
      this.$router.push(
        `/questions?searchBy=${this.keyword}&scope=${this.selectedScope.value}`
      );
    },

    getCurrentScope(scope) {
      if (scope === "TITLE") {
        return { name: "제목", value: "TITLE" };
      } else if (scope === "CONTENT") {
        return { name: "내용", value: "CONTENT" };
      } else {
        return { name: "제목 + 내용", value: "BOTH" };
      }
    }
  }
};
</script>

<style scoped>
.filters {
  width: 100px;
  max-width: 16%;
  margin-right: 15px;
}
.search-bar {
  max-width: 60%;
  margin: 35px auto 0 auto;
  display: flex;
  justify-content: center;
  align-items: baseline;
}
.icon {
  font-size: 25px;
  margin-left: 5px;
}

.icon:hover {
  cursor: pointer;
  color: #87bdd6;
}
</style>
