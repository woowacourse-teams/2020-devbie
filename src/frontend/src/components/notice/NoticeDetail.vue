<template>
  <div class="notice-detail">
    <div class="inner">
      <div class="notice-detail-main">
        <div class="notice-header">
          <div class="notice-title">
            <h1>
              {{ fetchedNotice.id }}. [ {{ fetchedNotice.noticeType }} ] -
              {{ fetchedNotice.title }}
            </h1>
          </div>
          <div class="notice-body">
            <div class="notice-img">
              <v-img
                :src="
                  'https://images.velog.io/images/sonypark/post/80241b72-4ffe-4223-a775-41c34dd6aed7/woowa-dev.jpeg'
                "
                class="white--text align-end"
                height="200px"
              >
              </v-img>
            </div>
            <div class="notice-buttons">
              <v-btn id="apply-btn" depressed large color="primary"
                >지원하기</v-btn
              >
              <v-btn id="chatting-btn" depressed large color="primary"
                >채팅방</v-btn
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
              연봉: {{ fetchedNotice.company.salary }}
            </p>
            <p class="infos">
              <i class="fas fa-calendar-alt"></i>
              지원기간: {{ fetchedNotice.duration }}
            </p>
            <p class="infos">
              <i class="fas fa-keyboard"></i>
              포지션: {{ fetchedNotice.jobPosition }}
            </p>
            <p class="infos">
              <i class="fas fa-burn"></i>
              프로그래밍 언어: {{ fetchedNotice.noticeDescription.languages }}
            </p>
            <p class="infos">
              {{ fetchedNotice.noticeDescription.content }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  created() {
    const noticeId = this.$route.params.id;
    this.$store.dispatch("FETCH_NOTICES");
    this.$store.dispatch("FETCH_NOTICE", noticeId);
  },
  computed: {
    ...mapGetters(["fetchedNotice"])
  }
};
</script>

<style scoped>
.notice-detail {
  align-items: center;
  margin-left: 20px;
}

.inner {
  display: flex;
  justify-content: center;
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
  padding: 30px 50px;
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
</style>