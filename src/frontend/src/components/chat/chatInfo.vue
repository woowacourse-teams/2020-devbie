<template>
  <div id="default_box">
    <div id="title_box">
      {{ fetchedChatTitle }}
    </div>
    <div id="count_box">
      10명
    </div>
    <div id="button_box">
      <v-btn large icon @click="closeChatDrawer"
        ><i class="fas fa-times"
      /></v-btn>
    </div>
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
    ...mapGetters(["fetchedChatTitle"])
  }
};
</script>

<style scoped>
#default_box {
  height: 50px;
  display: flex;
  align-items: center;
  padding: 10px;
}
#title_box {
  flex-basis: 180px;
  font-size: 16px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
#count_box {
  padding-left: 14px;
  flex-basis: 60px;
}
#button_box {
  flex-basis: 40px;
}
</style>
