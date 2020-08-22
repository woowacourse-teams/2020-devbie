<template>
  <div>
    <v-navigation-drawer absolute right permanent>
      <v-card min-height="100%" max-height="100%">
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
        <v-divider></v-divider>
        <chat-list></chat-list>
        <v-divider></v-divider>
        <chat-input></chat-input>
      </v-card>
    </v-navigation-drawer>
  </div>
</template>

<script>
import ChatList from "./ChatList";
import ChatInput from "./ChatInput";

export default {
  methods: {
    closeChatDrawer() {
      this.$store.dispatch("CLOSE_DRAWER");
    }
  },
  components: {
    ChatInput,
    ChatList
  }
};
</script>

<style scoped>
.chat-drawer-header {
  max-lines: 1;
}
</style>
