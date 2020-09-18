<template>
  <div class="default_box">
    <div class="title_box">
      {{ fetchedChatTitle }}
    </div>
    <v-spacer></v-spacer>
    <div class="count_box">{{ fetchedUserCount }}명</div>
    <v-btn large icon @click="closeChatDrawer"
      ><i class="fas fa-times close-icon"
    /></v-btn>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  methods: {
    closeChatDrawer() {
      this.$store.dispatch("CLOSE_DRAWER");
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
}

.close-icon {
  padding-top: 2px;
  font-size: 21px;
}
</style>
