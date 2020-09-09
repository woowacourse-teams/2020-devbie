<template>
  <div id="chat_box">
    <div id="nickName_box" v-bind:style="{ color: color }">{{ chat.name }}</div>
    <div id="message_box">{{ chat.message }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      color: ""
    };
  },
  created() {
    this.color = this.chat.titleColor;
  },
  props: {
    chat: {
      type: Object,
      required: true
    }
  }
};
</script>

<style scoped>
#chat_box {
  margin: 10px;
  font-family: "Nanum Gothic", sans-serif;
}

#nickName_box {
  margin-top: 18px;
  font-size: 13px;
  font-weight: 500;
}

#message_box {
  background-color: #e0e0e0;
  width: max-content;
  max-width: 85%;
  word-wrap: break-word;
  border-radius: 4px;
  font-size: 12px;
}
</style>
