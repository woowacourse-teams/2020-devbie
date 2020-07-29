<template>
  <div class="filter-box">
    <v-select
      class="filters"
      :items="position"
      hide-details
      label="직군"
      menu-props="auto"
      single-line
      v-model="e2"
    ></v-select>
    <v-select
      class="filters"
      v-model="e1"
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
      languages: ["C", "C++", "JAVA"],
      position: ["프론트엔드", "백엔드", "인프라"]
    };
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
}
</style>
