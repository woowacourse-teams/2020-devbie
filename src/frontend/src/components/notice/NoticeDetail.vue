<template>
  <div class="notice-detail">
    <v-divider></v-divider>
    <div class="inner">
      <div class="notice-detail-main">
        <div class="notice-header">
          <div class="notice-title">
            <h1 class="big-font">
              [ {{ fetchedNotice.noticeType }} ]
              {{ fetchedNotice.title }}
            </h1>
          </div>
          <div class="notice-body">
            <div class="notice-img">
              <v-img
                :src="fetchedNotice.image"
                class="white--text align-end"
                width="300px"
                height="200px"
              >
              </v-img>
            </div>
            <div class="notice-buttons">
              <v-btn id="apply-btn" depressed large color="#DAEBEA"
                >지원하기</v-btn
              >
              <v-btn
                id="chatting-btn"
                depressed
                large
                color="#DAEBEA"
                @click="openChatDrawer"
                >채팅방</v-btn
              >
              <v-btn
                class="admin-btn"
                depressed
                large
                color="warning"
                v-if="isAdmin()"
                @click="onEditNotice"
                >수정</v-btn
              >
              <v-btn
                class="admin-btn"
                depressed
                large
                color="warning"
                v-if="isAdmin()"
                @click="onDeleteNotice"
                >삭제</v-btn
              >
            </div>
          </div>
        </div>
        <div class="notice-content">
          <div class="notice-info">
            <p class="infos">
              <i class="fas fa-user-edit"></i>
              회사명: {{ fetchedNotice.company.name }}
            </p>
            <p class="infos">
              <i class="fas fa-won-sign"></i>
              연봉: {{ fetchedNotice.company.salary }} 만원
            </p>
            <p class="infos">
              <i class="fas fa-calendar-alt"></i>
              지원기간: {{ setDuration }}
            </p>
            <p class="infos">
              <i class="fas fa-keyboard"></i>
              포지션: {{ fetchedNotice.jobPosition }}
            </p>
            <p class="infos"><i class="fas fa-burn"></i>언어:</p>
            <p class="infos">
              <template v-for="(line, index) in content">
                {{ line }}
                <br :key="index" />
              </template>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import router from "../../router";

export default {
  data() {
    return {
      stompClient: {}
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser", "fetchedNotice"]),

    content() {
      return this.fetchedNotice.noticeDescription.content.split("\n");
    },

    setDuration() {
      if (this.fetchedNotice.duration === null) {
        return "상시모집";
      }

      const start = new Date(
        this.fetchedNotice.duration.startDate
      ).toLocaleDateString();
      const end = new Date(
        this.fetchedNotice.duration.endDate
      ).toLocaleDateString();

      return (
        (this.fetchedNotice.duration.startDate === null ? "" : start) +
        " ~ " +
        (this.fetchedNotice.duration.endDate === null ? "모집시" : end)
      );
    }
  },

  created() {
    const noticeId = this.$route.params.id;
    try {
      this.$store.dispatch("FETCH_NOTICE", noticeId);
    } catch (error) {
      console.log("공고 불러오기 실패 " + error.response.data.message);
      this.$store.dispatch(
        "UPDATE_SNACKBAR_TEXT",
        "공고를 불러오지 못했습니다."
      );
    }
  },

  methods: {
    isAdmin() {
      return this.fetchedLoginUser.roleType === "ADMIN";
    },
    async onDeleteNotice() {
      await this.$store.dispatch("DELETE_NOTICE", this.$route.params.id);
      await router.go(0); // 새로고침
    },
    onEditNotice() {
      router.push(`/notices/edit/${this.$route.params.id}`);
    },
    openChatDrawer() {
      this.$store.dispatch("OPEN_DRAWER", this.fetchedNotice);
    }
  }
};
</script>

<style scoped>
.notice-detail {
  margin-top: 50px;
  align-items: center;
  width: 40%;
  min-width: 200px;
}

.inner {
  display: flex;
  justify-content: start;
  width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
}

.notice-detail-main {
  display: flex;
  flex-direction: column;
}

.notice-header {
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
  font-family: "Jua", sans-serif;
}

.notice-body {
  display: flex;
  justify-content: center;
  align-content: space-between;
}

.notice-info {
  display: flex;
  justify-content: flex-end;
}

.notice-info {
  display: flex;
  flex-direction: column;
}

.notice-info .infos {
  font-size: 16px;
  margin-right: 15px;
}

.question-info .infos:last-child {
  margin-right: 5px;
}

.notice-content {
  padding: 30px 15px;
}

.notice-buttons {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 10px;
}

#apply-btn {
  width: 100px;
  padding: 10px;
  margin: 3px 3px;
}

#chatting-btn {
  width: 100px;
  padding: 10px;
  margin: 3px 3px;
}

.admin-btn {
  width: 100px;
  padding: 10px;
  margin: 3px 3px;
}

.fas {
  width: 15px;
  height: 15px;
  padding-right: 20px;
}

.big-font {
  font-size: 20px;
}
</style>
