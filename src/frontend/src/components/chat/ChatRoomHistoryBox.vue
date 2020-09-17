<template>
  <div>
    <v-list>
      <v-list-item
        v-for="item in fetchedChatRoomHistory"
        :key="item.noticeId"
        @click="connectChat(item)"
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
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedChatRoomHistory"])
  },
  methods: {
    connectChat(chatRoomHistory) {
      const noticeId = chatRoomHistory.noticeId;
      const companyName = chatRoomHistory.chatRoomName.split(" - ")[0];
      const noticeTitle = chatRoomHistory.chatRoomName.split(" - ")[1];

      const notice = {
        id: noticeId,
        company: {
          name: companyName
        },
        title: noticeTitle
      };

      this.$store.dispatch("OPEN_CHAT_DRAWER", notice);
    },
    deleteChatRoomHistory(noticeId) {
      this.$store.dispatch("DELETE_CHAT_ROOM_HISTORY", noticeId);
    }
  }
};
</script>

<style scoped></style>
