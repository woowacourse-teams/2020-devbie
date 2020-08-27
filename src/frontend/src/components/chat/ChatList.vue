<template>
  <div id="default_box">
    <v-list ref="chatList" id="chats">
      <template v-for="item in fetchedChats">
        <chat-item v-bind:key="item.id" :chat="item" />
      </template>
    </v-list>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ChatItem from "./ChatItem";

export default {
  computed: {
    ...mapGetters(["fetchedChats"])
  },
  mounted() {
    this.$refs.chatList.$el.scrollTop = this.$refs.chatList.$el.scrollHeight;
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
#default_box {
  height: 100%;
  position: absolute;
}
#chats {
  height: 100%;
  width: 260px;
  overflow: auto;
}
</style>
