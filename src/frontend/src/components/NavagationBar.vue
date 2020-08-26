<template>
  <div class="navigation-bar">
    <v-app-bar color="#9FD0D4" name="navigation">
      <v-toolbar-title id="home-box"
        ><v-btn @click="$router.push('/')" text x-large
          ><p id="home-title">Devbie</p></v-btn
        ></v-toolbar-title
      >
      <v-spacer></v-spacer>
      <v-btn @click="$router.push('/admin')" v-if="isAdmin()" text x-large
        ><p class="navigation-menu">관리자</p></v-btn
      >
      <v-btn @click="$router.push('/notices')" text x-large
        ><p class="navigation-menu">공고</p></v-btn
      >
      <v-btn @click="$router.push('/questions?orderBy=CREATED_DATE')" text x-large
        ><p class="navigation-menu">면접 질문</p></v-btn
      >
      <template v-if="isLoggedIn">
        <v-menu transition="slide-y-transition" offset-y bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-avatar>
              <v-img
                :src="fetchedLoginUser.image"
                alt="avatar-image"
                v-bind="attrs"
                v-on="on"
              />
            </v-avatar>
          </template>
          <v-list>
            <v-list-item @click="$router.push('/mypage')">
              마이페이지
            </v-list-item>
            <v-list-item @click="$router.push('/favorites/notice')">
              즐겨찾기
            </v-list-item>
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
import router from "../router";

export default {
  computed: {
    ...mapGetters(["fetchedLoginUser", "isLoggedIn"])
  },

  methods: {
    isAdmin() {
      if (this.isLoggedIn) {
        return this.fetchedLoginUser.roleType === "ADMIN";
      }
      return false;
    },

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
      router.push("/");
    }
  }
};
</script>

<style scoped>
#home-box {
  margin-left: 50px;
}

#home-title {
  font-size: 28px;
  color: #f4f4f4;
  margin: 0;
  padding: 0;
}

.navigation-menu {
  font-family: "Do Hyeon", sans-serif;
  font-size: 24px;
  color: #f4f4f4;
  margin: 0;
  padding: 0;
}

#login-btn {
  margin-right: 40px;
}
</style>
