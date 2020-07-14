<template>
  <div>
    <v-app-bar color="#87BDD6" name="navigation">
      <v-app-bar-nav-icon id="logo">icon</v-app-bar-nav-icon>
      <v-toolbar-title id="home-title">Devbie</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text x-large><p class="navigation-menu">공고</p></v-btn>
      <v-btn text x-large><p class="navigation-menu">면접</p></v-btn>
      <v-btn large color="#E8E8E8" id="login-btn">Login with Github</v-btn>
    </v-app-bar>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#logo {
  margin-left: 50px;
}
#home-title {
  font-size: 28px;
  color: #f4f4f4;
}
.navigation-menu {
  font-size: 24px;
  color: #f4f4f4;
}
#login-btn {
  margin-right: 40px;
}
</style>
