<template>
  <div>
    <v-navigation-drawer v-model="drawer" absolute right temporary>
      <v-card>
        <v-container>
          <v-row style="flex-wrap: nowrap;">
            <v-col cols="7" class="flex-grow-1">
              <p style="text-align: center">
                제목은이쁘게
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
        </v-container>
      </v-card>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["drawer"])
  },
  methods: {
    closeChatDrawer() {
      this.$store.dispatch("TOGGLE_DRAWER");
    }
  }
};
</script>

<style scoped>
.chat-drawer-header {
  max-lines: 1;
}
</style>
