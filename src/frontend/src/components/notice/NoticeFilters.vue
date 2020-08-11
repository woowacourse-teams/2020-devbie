<template>
  <div class="filter-box">
    <br />
    <v-select
      class="filters"
      v-model="selectedPosition"
      v-on:change="changeJobPosition"
      item-text="name"
      item-value="value"
      :items="position"
      hide-details
      menu-props="auto"
      single-line
    ></v-select>
    <v-select
      class="filters"
      v-model="selectedLanguage"
      v-on:change="changeLanguage"
      item-text="name"
      item-value="value"
      :items="languages"
      menu-props="auto"
      label="언어"
      hide-details
      single-line
    ></v-select>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedPosition: "",
      selectedLanguage: "",
      position: [
        { name: "무관", value: "" },
        { name: "프론트엔드", value: "FRONTEND" },
        { name: "백엔드", value: "BACKEND" }
      ],
      languages: [
        { name: "무관", value: "" },
        { name: "C", value: "C" },
        { name: "C++", value: "CPP" },
        { name: "JAVA", value: "JAVA" },
        { name: "Python", value: "PYTHON" }
      ]
    };
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
}

.filters {
  padding: 0;
  margin: 0 50px 0 0;
  width: 12em;
  max-width: 12em;
}
</style>
