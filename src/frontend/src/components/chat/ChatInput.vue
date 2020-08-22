<template>
  <div class="d-flex flex-column">
    <div>
      {{ fetchedNickName }}
    </div>
    <div>
      <v-text-field
        label="Solo"
        placeholder="욕설은 삼가해주세요"
        solo
        background-color="grey"
        height="80px"
        v-on:keyup.enter="submit"
        v-model="chatMessage"
      ></v-text-field>
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

<style scoped></style>
