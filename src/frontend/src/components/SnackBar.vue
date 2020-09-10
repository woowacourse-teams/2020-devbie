<template>
  <div class="inner">
    <v-snackbar
      v-model="fetchedSnackBarState"
      :multi-line="true"
      timeout="0"
      top
      centered
      class="snack-bar"
      :class="$mq"
    >
      {{ fetchedSnackBarText }}

      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="closeSnackBar">
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "snackBar",
  computed: {
    ...mapGetters(["fetchedSnackBarState", "fetchedSnackBarText"])
  },
  methods: {
    closeSnackBar: function() {
      this.$store.dispatch("UPDATE_SNACKBAR_STATE", false);
    }
  }
};
</script>

<style scoped>
.snack-bar.mobile {
  margin: 0 auto;
}
</style>
