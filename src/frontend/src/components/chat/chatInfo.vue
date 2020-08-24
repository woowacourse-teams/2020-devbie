<template>
  <div>
    <v-row style="flex-wrap: nowrap;">
      <v-col cols="7" class="flex-grow-1">
        <p style="text-align: center">
          {{ fetchedNoticeTitle }}
        </p>
      </v-col>
      <v-col cols="3">
        <p>
          30명
        </p>
      </v-col>
      <v-col cols="2" class="flex-shrink-1">
        <v-btn smallv absolute right icon @click="closeChatDrawer"
          ><i class="fas fa-times"></i
        ></v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    closeChatDrawer() {
      this.$store.dispatch("CLOSE_DRAWER");
    }
  },
  computed: {
    ...mapGetters(["fetchedNoticeTitle"])
  }
};
</script>

<style scoped></style>
