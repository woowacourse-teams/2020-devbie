<template>
  <div class="notice-detail">
    <div class="inner">
      <div class="notice-header">
        <div class="notice-title">
          <h1>{{ fetchedNotice.id }}. {{ fetchedNotice.title }}</h1>
        </div>
        <div class="notice-body">
          <div class="notice-img">
            <v-img
              :src="
                'https://lh3.googleusercontent.com/proxy/HXa2WVvTGp1V_6BW1ES-7Y7TUHsct7qJNYomuYj2RjDwfEHmcHcw49pnT0CRs4yfcws1jUbihqb91pLpzSpoaYrC3muq9S-orxymfKhAiELXFjFvct1z1sWY7unsNEnQKQHxPWNoAxellpFBssAM3uS0YYXyemex0pri_JkWWGi94P9Ijjs-3Xgf7dAJIWAdY6QmbakgeJDMMpT5vIfp2QO9TgzAtwffeCkz0Q7u6MoeVpIj_YfSTAiXpVO3yZq3exVA62syYhGxvt8K_oORsfQNB6tXir5PTjYMoghmyirLULbtbEhw9-7sAonCbXQrsqT2okQXJdsvR4gPGJS8q7CvlYOjFiWztqDAuTMQilzO9mmrBWYqgrzYpZ_WeQMkeL6z0BsbUeTOPrh1VF7kk8g_6P6M17B345Rir1hiim3fHMC8Is4Y8ViZOLUv34MbVld6TkkHty9itNl2MVCJTwuZpw'
              "
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
            </v-img>
          </div>
          <div class="notice-info">
            <p class="infos">
              <i class="fas fa-user-edit"></i>
              {{ fetchedNotice.company }}
            </p>
            <p class="infos">
              <i class="fas fa-eye"></i>
              {{ fetchedNotice.duration }}
            </p>
            <p class="infos">
              <i class="fas fa-eye"></i>
              {{ fetchedNotice.jobPosition }}
            </p>
            <p class="infos">
              <i class="fas fa-eye"></i>
              {{ fetchedNotice.noticeDescription }}
            </p>
          </div>
        </div>
      </div>
      <div class="notice-content">
        <p>내용 : {{ fetchedNotice.noticeDescription.content }}</p>
      </div>
      {{ fetchedNotice }}
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["fetchedNotice"])
  },
  created() {
    const noticeId = this.$route.params.id;
    this.$store.dispatch("FETCH_NOTICE", noticeId);
  }
};
</script>

<style scoped>
.notice-detail {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 20px;
}

.notice-header {
  padding: 18px;
  border-bottom: solid 1px #e8e8e8;
}

.notice-body {
  display: flex;
}

.notice-info {
  display: flex;
  justify-content: flex-end;
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

.inner {
  width: 90%;
  box-sizing: border-box;
  padding: 10px 0 40px 0;
  border-bottom: solid 1px #e8e8e8;
}
</style>
