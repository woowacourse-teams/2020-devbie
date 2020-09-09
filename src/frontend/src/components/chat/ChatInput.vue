<template>
  <div id="default_box">
    <div id="name_box">
      {{ fetchedNickName }}
    </div>
    <div id="input_box">
      <v-textarea
        placeholder="욕설은 삼가해주세요"
        background-color="#e0e0e0"
        rows="3"
        row-height="12"
        v-on:keyup.enter="submit"
        v-model="chatMessage"
      >
      </v-textarea>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      chatMessage: ""
    };
  },
  computed: {
    ...mapGetters(["fetchedNickName"])
  },
  methods: {
    submit() {
      this.$store.dispatch("SEND", {
        name: this.fetchedNickName,
        message: this.chatMessage
      });
      this.chatMessage = "";
    }
  }
};
</script>

<style scoped>
#default_box {
  margin: 3px;
}
#name_box {
  margin: 3px;
}
#input_box {
  margin: 5px 3px 3px;
}
</style>
