<template>
  <div class="default_box">
    <div class="title_box">{{ "내 채팅" }}</div>
    <v-btn large icon @click="closeChatRoomDrawer"
      ><i class="fas fa-times close-icon"
    /></v-btn>
  </div>
</template>

<script>
export default {
  name: "ChatRoomTitleBox",
  methods: {
    closeChatRoomDrawer() {
      this.$store.dispatch("CLOSE_CHAT_ROOM_DRAWER");
    }
  },
  computed: {}
};
</script>

<style scoped></style>
