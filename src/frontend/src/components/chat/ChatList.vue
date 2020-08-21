<template>
  <div>
    <v-virtual-scroll :items="fetchedChats" :item-height="50" height="660">
      <template v-slot="{ item }">
        <chat-item :chat="item" />
      </template>
    </v-virtual-scroll>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ChatItem from "./ChatItem";

export default {
  computed: {
    ...mapGetters(["fetchedChats"])
  },
  components: { ChatItem }
};
</script>

<style scoped></style>
