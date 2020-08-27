<template>
  <v-app id="app">
    <div :class="{ main_box: this.drawer }">
      <navigation-bar @logout="logout"></navigation-bar>
      <transition name="fade" mode="out-in">
        <router-view class="content"></router-view>
      </transition>
      <snack-bar></snack-bar>
      <footer-bar></footer-bar>
    </div>
    <chat-drawer v-if="drawer"></chat-drawer>
    <button id="drawer_btn" v-else @click="openDrawer">
      채팅방 열기
    </button>
  </v-app>
</template>

<script>
import { mapGetters } from "vuex";
import NavigationBar from "./components/NavagationBar.vue";
import FooterBar from "./components/FooterBar.vue";
import SnackBar from "./components/SnackBar";
import ChatDrawer from "./components/chat/ChatDrawer";

export default {
  async beforeCreate() {
    const token = localStorage.getItem("devbieToken");
    if (token) {
      try {
        await this.$store.dispatch("FETCH_LOGIN_USER");
      } catch (error) {
        console.log(error.response.data.message);
        this.$store.dispatch("UPDATE_SNACKBAR_TEXT", "로그인 실패하였습니다.");
        localStorage.removeItem("devbieToken");
        this.$store.commit("DELETE_LOGIN_USER");
      }
    }
  },
  created() {
    window.addEventListener("beforeunload", this.closeDrawer);
  },
  methods: {
    logout() {
      localStorage.removeItem("devbieToken");
      this.$store.commit("DELETE_LOGIN_USER");
    },
    openDrawer() {
      this.$store.dispatch("OPEN_LATEST");
    },
    closeDrawer() {
      this.$store.dispatch("CLOSE_DRAWER");
    }
  },
  computed: {
    ...mapGetters(["drawer"])
  },
  components: {
    ChatDrawer,
    NavigationBar,
    FooterBar,
    SnackBar
  }
};
</script>

<style>
#app {
  font-family: "Noto Sans KR", "Noto Sans JP", sans-serif;
  color: #35495e;
}

.content {
  min-height: calc(100vh - 220px);
}

a {
  text-decoration: none;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.main_box {
  width: calc(100vw - 260px);
}

#drawer_btn {
  position: fixed;
  bottom: 0;
  right: 0;
  width: 120px;
  height: 40px;
  color: white;
  text-align: center;
  background-color: #00b8d4;
  padding: 10px;
  border-radius: 4px;
}
</style>
