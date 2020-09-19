<template>
  <div class="default_box">
    <v-btn icon @click="backToChatRoomDrawer"
      ><i class="fas fa-chevron-left back-icon"
    /></v-btn>
    <div class="title_box">
      {{ fetchedChatTitle }}
    </div>
    <v-spacer></v-spacer>
    <div class="count_box">{{ fetchedUserCount }}명</div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    backToChatRoomDrawer() {
      this.$store.dispatch("SHOW_CHAT_ROOMS_DRAWER");
    }
  },
  computed: {
    ...mapGetters(["fetchedChatTitle", "fetchedUserCount"])
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
.count_box {
  display: flex;
  flex-direction: row-reverse;
  flex-basis: 60px;
  padding-right: 5px;
}
.back-icon {
  padding-top: 2px;
}
</style>
