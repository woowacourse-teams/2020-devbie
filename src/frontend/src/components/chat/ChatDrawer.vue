<template>
  <div>
    <v-navigation-drawer fixed right permanent class="drawer" :class="$mq">
      <div id="chat_components">
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

export default {
  components: {
    ChatInfo,
    ChatInput,
    ChatList
  }
};
</script>

<style scoped>
#chat_components {
  height: 100%;
  display: flex;
  flex-direction: column;
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
