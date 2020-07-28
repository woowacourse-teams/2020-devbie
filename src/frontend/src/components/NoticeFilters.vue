<template>
  <div class="filter-box">
    <v-select
      class="filters"
      :items="e1"
      hide-details
      label="직군"
      menu-props="auto"
      single-line
      v-model="e2"
    ></v-select>
    <v-select
      class="filters"
      v-model="e1"
      :items="states"
      menu-props="auto"
      label="Select"
      hide-details
      single-line
    ></v-select>
  </div>
</template>

<script>
export default {
  data() {
    return {
      e1: "Florida",
      e2: "Texas",
      e3: null,
      e4: null,
      items: [
        { text: "State 1" },
        { text: "State 2" },
        { text: "State 3" },
        { text: "State 4" },
        { text: "State 5" },
        { text: "State 6" },
        { text: "State 7" }
      ],
      states: [
        "Alabama",
        "Alaska",
        "American Samoa",
        "Arizona",
        "Arkansas",
        "California"
      ]
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
  margin: 0;
  padding: 0;
}
</style>
