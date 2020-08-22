<template>
  <div>
    <v-card-text>
      <v-list ref="chatList" id="chats">
        <template v-for="item in fetchedChats">
          <chat-item v-bind:key="item.id" :chat="item" />
        </template>
      </v-list>
    </v-card-text>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ChatItem from "./ChatItem";

export default {
  computed: {
    ...mapGetters(["fetchedChats"])
  },
  watch: {
    fetchedChats() {
      setTimeout(() => {
        this.$refs.chatList.$el.scrollTop = this.$refs.chatList.$el.scrollHeight;
      }, 0);
    }
  },
  components: { ChatItem }
};
</script>

<style scoped>
#chats {
  height: 630px;
  overflow: auto;
}
</style>
