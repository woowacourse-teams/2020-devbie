<template>
  <div class="filter-box" :class="$mq">
    <br />
    <v-select
      class="filters"
      :class="$mq"
      v-model="selectedPosition"
      v-on:change="changeJobPosition"
      item-text="text"
      item-value="key"
      :items="fetchedJobPositions"
      hide-details
      label="직군"
      menu-props="auto"
      single-line
    ></v-select>
    <v-select
      class="filters"
      :class="$mq"
      v-model="selectedLanguage"
      v-on:change="changeLanguage"
      item-text="text"
      item-value="key"
      :items="fetchedLanguages"
      menu-props="auto"
      label="언어"
      hide-details
      single-line
    ></v-select>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      selectedPosition: "",
      selectedLanguage: ""
    };
  },

  computed: {
    ...mapGetters(["fetchedLanguages", "fetchedJobPositions"])
  },

  created() {
    this.$store.dispatch("FETCH_FILTERS");
  },

  methods: {
    changeJobPosition() {
      this.$store.commit("SET_JOB_POSITION", this.selectedPosition);
    },
    changeLanguage() {
      this.$store.commit("SET_LANGUAGE", this.selectedLanguage);
    }
  }
};
</script>

<style scoped>
.filter-box {
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Noto Sans KR", "Noto Sans JP", sans-serif;
  margin-right: 120px;
}

.filters {
  padding: 0;
  margin: 0 50px 0 0;
  width: 12em;
  max-width: 12em;
}

.filter-box.mobile {
  margin-right: 0px;
  flex-flow: column;
}

.filters.mobile {
  margin: 0 0 15px 0;
}
</style>
