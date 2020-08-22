<template>
  <div>
    <v-navigation-drawer fixed right permanent style="height: 100vh">
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
#chat_info_box {
  flex-basis: 60px;
}
#chat_list_box {
  flex-basis: 500px;
  flex-grow: 1;
  position: relative;
}
#chat_input_box {
  flex-basis: 120px;
}
</style>
