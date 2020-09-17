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
  methods: {
    closeChatRoomDrawer() {
      this.$store.dispatch("CLOSE_CHAT_ROOM_DRAWER");
    }
  }
};
</script>

<style scoped>
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
</style>
