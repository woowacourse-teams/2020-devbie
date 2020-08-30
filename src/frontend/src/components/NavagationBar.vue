<template>
  <div class="navigation-bar" :class="$mq">
    <v-app-bar color="#9FD0D4" name="navigation" class="inner" :class="$mq">
      <v-toolbar-title class="home-box" :class="$mq"
        ><v-btn
          @click="$router.push('/')"
          text
          x-large
          class="home-btn"
          :class="$mq"
          ><p class="home-title" :class="$mq">Devbie</p></v-btn
        ></v-toolbar-title
      >
      <v-spacer></v-spacer>
      <a
        @click="$router.push('/admin')"
        v-if="isAdmin()"
        class="menu navigation-menu"
        :class="$mq"
        >관리자</a
      >
      <a
        @click="$router.push('/notices')"
        class="menu navigation-menu"
        :class="$mq"
        >공고</a
      >
      <a
        @click="$router.push('/questions?orderBy=CREATED_DATE')"
        class="menu navigation-menu last"
        :class="$mq"
        >면접 질문</a
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
      <v-btn
        @click="showLoginPage"
        color="#fff"
        class="login-btn"
        :class="$mq"
        v-else
      >
        <i class="fab fa-github login-icon" :class="$mq"></i>
        <span class="login-text" :class="$mq">{{ loginText }}</span>
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
    ...mapGetters(["fetchedLoginUser", "isLoggedIn"]),

    loginText() {
      return this.$mq === "mobile" ? "Login" : "Login With Github";
    }
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
.navigation-bar.mobile {
  max-width: 100%;
}

.inner {
  padding: 0 2%;
}

.inner.mobile {
  padding: 0;
  margin: 0 auto;
}

.home-box {
  margin-left: 20px;
}

.home-box.mobile {
  margin-left: 0;
}

.home-btn {
  padding: 0 !important;
}

.home-title {
  font-size: 28px;
  color: #f4f4f4;
  margin: 0;
  padding: 0;
}

.home-title.mobile {
  font-size: 17px !important;
}

.home-btn.mobile {
  padding: 0 !important;
  margin-left: 0 !important;
}

.navigation-menu {
  font-family: "Do Hyeon", sans-serif;
  font-size: 24px;
  color: #f4f4f4;
  margin: 0;
  padding: 4px 15px 0 15px;
}

.navigation-menu.last {
  margin-right: 15px;
}

.navigation-menu.last.mobile {
  margin-right: 0 !important;
}

.navigation-menu.mobile {
  font-size: 16px;
  padding: 4px 12px 0 5px;
}

.login-icon {
  font-size: 25px;
  margin-right: 4px;
}

.login-icon.mobile {
  font-size: 16px;
  margin-right: 2px;
}

.login-btn.mobile {
  margin: 0;
  max-width: 80px !important;
}

.profile {
}
</style>
