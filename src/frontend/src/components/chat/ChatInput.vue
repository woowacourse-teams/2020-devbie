<template>
  <div style="height:100%" class="d-flex flex-column">
    <div>
      익명의 이름
    </div>
    <div>
      <v-text-field
        label="Solo"
        placeholder="욕설은 삼가해주세요"
        solo
        background-color="grey"
        height="100px"
        v-on:keyup.enter="submit"
        v-model="chatMessage"
      ></v-text-field>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      chatMessage: ""
    };
  },
  methods: {
    submit() {
      this.$store.dispatch("SEND", {
        name: "유안",
        message: this.chatMessage
      });
      this.chatMessage = "";
    }
  }
};
</script>

<style scoped></style>
