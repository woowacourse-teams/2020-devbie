<template>
  <div>
    <v-app-bar color="#87BDD6" name="navigation">
      <v-app-bar-nav-icon @click="$router.push('/')" id="logo"
        >icon
      </v-app-bar-nav-icon>
      <v-toolbar-title id="home-title">Devbie</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="$router.push('/notice')" text x-large
        ><p class="navigation-menu">공고</p></v-btn
      >
      <v-btn @click="$router.push('/questions')" text x-large
        ><p class="navigation-menu">면접</p></v-btn
      >
      <template v-if="isLoggedIn">
        <v-menu transition="slide-y-transition" bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-avatar color="primary">
              <v-img
                :src="fetchedLoginUser.image"
                alt="avatar-image"
                v-bind="attrs"
                v-on="on"
              />
            </v-avatar>
          </template>
          <v-list>
            <v-list-item> 마이페이지 </v-list-item>
            <v-list-item @click="logout"> 로그아웃 </v-list-item>
          </v-list>
        </v-menu>
      </template>
      <v-btn @click="showLoginPage" color="#E8E8E8" id="login-btn" large v-else
        >Login with Github
      </v-btn>
    </v-app-bar>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  props: ["isLoggedIn"],

  computed: {
    ...mapGetters(["fetchedLoginUser"])
  },
  methods: {
    async showLoginPage() {
      try {
        const redirectUrlData = await axios.get("/api/auth/login-url");
        window.location.href = await redirectUrlData.data;
      } catch (error) {
        console.error("로그인 화면 로딩 실패");
      }
    },
    logout() {
      this.$emit("logout");
    }
  }
};
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
  margin: 0;
  padding: 0;
}

#login-btn {
  margin-right: 40px;
}
</style>
