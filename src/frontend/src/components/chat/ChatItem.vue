<template>
  <div id="chat_box">
    <div id="nickName_box" v-bind:style="{ color: color }">{{ chat.name }}</div>
    <div id="message_box">
      {{ chat.message }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      colors: [
        "#F44336",
        "#E91E63",
        "#9C27B0",
        "#673AB7",
        "#3F51B5",
        "#2196F3",
        "#009688",
        "#4CAF50",
        "#FFC107",
        "#FF9800",
        "#4D2F40",
        "#795548",
        "#9E9E9E"
      ],
      color: ""
    };
  },
  created() {
    this.color = this.colors[Math.floor(Math.random() * this.colors.length)];
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
