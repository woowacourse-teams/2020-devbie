<template>
  <div>
    <v-navigation-drawer fixed right permanent class="drawer" :class="$mq">
      <div class="chat_components" v-if="fetchedChatRoomDrawer">
        <div class="default_box">
          <div class="title_box">{{ "내 채팅" }}</div>
          <v-btn large icon @click="closeChatRoomDrawer"
            ><i class="fas fa-times close-icon"
          /></v-btn>
        </div>
        <v-divider></v-divider>
        <v-list>
          <v-list-item
            v-for="item in fetchedChatRoomHistory"
            :key="item.noticeId"
          >
            <v-list-item-content>
              <v-list-item-title v-text="item.chatRoomName"></v-list-item-title>
            </v-list-item-content>
            <v-btn large icon @click="deleteChatRoomHistory(item.noticeId)"
              ><i class="fas fa-times close-icon"
            /></v-btn>
          </v-list-item>
        </v-list>
      </div>
      <div class="chat_components" v-else>
        <div id="chat_info_box">
          <chat-info></chat-info>
        </div>
        <v-divider></v-divider>
        <div id="chat_list_box">
          <chat-list></chat-list>
        </div>
        <v-divider></v-divider>
        <div id="chat_input_box">
          <chat-input></chat-input>
        </div>
      </div>
    </v-navigation-drawer>
  </div>
</template>

<script>
import ChatList from "./ChatList";
import ChatInput from "./ChatInput";
import ChatInfo from "./chatInfo";
import { mapGetters } from "vuex";

export default {
  components: {
    ChatInfo,
    ChatInput,
    ChatList
  },
  computed: {
    ...mapGetters(["fetchedChatRoomDrawer", "fetchedChatRoomHistory"])
  },
  methods: {
    closeChatRoomDrawer() {
      this.$store.dispatch("CLOSE_CHAT_ROOM_DRAWER");
    },
    deleteChatRoomHistory(noticeId) {
      this.$store.dispatch("DELETE_CHAT_ROOM_HISTORY", noticeId);
    }
  }
};
</script>

<style scoped>
.chat_components {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.default_box {
  height: 50px;
  display: flex;
  align-items: center;
  padding: 5%;
  justify-content: space-between;
}
.title_box {
  flex-basis: 180px;
  font-size: 19px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
#chat_list_box {
  flex-grow: 1;
  position: relative;
}
.drawer {
  height: 100vh;
  width: 260px;
}
.drawer.mobile {
  width: 100vw !important;
}
</style>
