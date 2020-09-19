<template>
  <div class="search-bar" :class="$mq">
    <v-select
      class="filters"
      :class="$mq"
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
      class="input-keyword"
      :class="$mq"
    >
    </v-text-field>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      scopes: [
        { name: "제목", value: "TITLE" },
        { name: "내용", value: "CONTENT" },
        { name: "제목 + 내용", value: "BOTH" }
      ],
      selectedScope: {
        name: "제목",
        value: "TITLE"
      },
      keyword: ""
    };
  },

  computed: {
    ...mapGetters(["fetchedSearchScope", "fetchedQuestionKeyword"]),

    title() {
      if (this.selectedScope.value !== "CONTENT") {
        return this.keyword;
      }
      return "";
    },

    content() {
      if (this.selectedScope.value !== "TITLE") {
        return this.keyword;
      }
      return "";
    }
  },

  created() {
    this.keyword = this.fetchedQuestionKeyword;
    this.selectedScope =
      this.fetchedSearchScope.length === 0
        ? {
            name: "제목",
            value: "TITLE"
          }
        : this.fetchedSearchScope && this.fetchedSearchScope[0];
  },

  methods: {
    searchByInput(event) {
      event.preventDefault();
      this.$router.push(
        `/questions?title=${this.title}&content=${this.content}`
      );
      this.$store.commit("SET_SEARCH_SCOPE", this.selectedScope);
      this.$store.commit("SET_KEYWORD", this.keyword);
    }
  }
};
</script>

<style scoped>
.search-bar {
  max-width: 60%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: baseline;
}

.search-bar.tablet {
  max-width: 80%;
}

.search-bar.mobile {
  max-width: 92%;
}

.filters {
  max-width: 16%;
  margin-right: 15px;
}

.filters.tablet {
  min-width: 30%;
}

.filters.mobile {
  max-width: 29%;
  font-size: 12px;
}

.input-keyword.mobile {
  font-size: 12px;
}
</style>
