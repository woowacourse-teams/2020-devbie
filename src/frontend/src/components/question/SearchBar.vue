<template>
  <div class="search-bar" :class="$mq">
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
export default {
  data() {
    return {
      keyword: ""
    };
  },

  methods: {
    searchByInput(event) {
      event.preventDefault();
      this.$router.push(`/questions?q=${this.keyword}`);
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

.input-keyword.mobile {
  font-size: 12px;
}
</style>
