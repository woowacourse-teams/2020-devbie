<template>
  <div>
    <v-footer dark padless color="#9FD0D4" id="footer">
      <v-card flat tile color="#9FD0D4" class="lighten-1 white--text">
        <v-card-text>
          <a
            href="https://www.facebook.com/Devbie-101790985021306/?notif_id=1600704082047732&notif_t=page_fan&ref=notif"
            ><v-icon class="sns" size="30px">{{ "mdi-facebook" }}</v-icon></a
          >
          <a href="https://github.com/woowacourse-teams/2020-devbie"
            ><v-icon class="sns" size="30px">{{ "mdi-github" }}</v-icon></a
          >
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          Copyright © {{ new Date().getFullYear() }} —
          <strong>Underdogs</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      icons: ["mdi-facebook", "mdi-github", "mdi-linkedin", "mdi-instagram"]
    };
  }
};
</script>

<style scoped>
.sns {
  margin: 0 10px;
}
#footer {
  min-height: 155px;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
