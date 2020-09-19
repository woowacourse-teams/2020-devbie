<template>
  <div class="notice-detail" :class="$mq">
    <v-divider></v-divider>
    <div class="inner" :class="$mq">
      <div class="notice-detail-main">
        <div class="notice-header">
          <div class="notice-title">
            <h1 class="big-font">
              <v-btn
                icon
                @click="$router.go(-1)"
                class="back-button"
                :class="$mq"
              >
                <v-icon large>
                  mdi-arrow-left-bold-circle-outline
                </v-icon>
              </v-btn>
              [ {{ notice.noticeType }} ]
              {{ notice.title }}
              <favorite-control
                class="heart-icon"
                :targetObjectId="notice.id"
                :isUserFavorite="isUserNoticeFavorites(notice.id)"
                :isQuestion="false"
              ></favorite-control>
            </h1>
          </div>
          <div class="notice-body" :class="$mq">
            <div class="notice-img">
              <v-img
                :src="notice.image"
                class="white--text align-end"
                width="300px"
                height="200px"
              >
              </v-img>
            </div>
            <div class="notice-buttons" :class="$mq">
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
              회사명: {{ notice.company.name }}
            </p>
            <p class="infos">
              <i class="fas fa-won-sign"></i>
              연봉: {{ notice.company.salary }} 만원
            </p>
            <p class="infos">
              <i class="fas fa-calendar-alt"></i>
              지원기간: {{ setDuration }}
            </p>
            <p class="infos">
              <i class="fas fa-keyboard"></i>
              포지션: {{ notice.jobPosition }}
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
import FavoriteControl from "../favorite/FavoriteControl";
import { getAction } from "@/api";

export default {
  props: ["id"],

  components: { FavoriteControl },

  data() {
    return {
      notice: {
        id: -1,
        company: { name: "", salary: 1 },
        title: "",
        noticeType: "",
        duration: null,
        jobPosition: "",
        noticeDescription: { languages: [], content: "" },
        image: ""
      }
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser", "isUserNoticeFavorites"]),

    content() {
      return this.notice.noticeDescription.content.split("\n");
    },

    setDuration() {
      if (this.notice.duration === null) {
        return "상시모집";
      }

      const startDate = new Date(
        this.notice.duration.startDate
      ).toLocaleDateString();
      const endDate = new Date(
        this.notice.duration.endDate
      ).toLocaleDateString();

      return (
        (this.notice.duration.startDate === null ? "" : startDate) +
        " ~ " +
        (this.notice.duration.endDate === null ? "모집시" : endDate)
      );
    }
  },

  watch: {
    async id() {
      await this.initialize();
    }
  },

  async mounted() {
    await this.initialize();
  },

  methods: {
    async initialize() {
      try {
        const { data } = await getAction(`/api/notices/${this.id}`);
        this.notice = data;
      } catch (error) {
        console.log("공고 불러오기 실패 " + error.response.data.message);
        await this.$store.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "공고를 불러오지 못했습니다."
        );
      }
    },

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
      this.$store.dispatch("OPEN_CHAT_DRAWER", this.notice);
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

.heart-icon {
  display: inline;
}

.back-button {
  display: none;
}

.notice-body.mobile {
  width: 100%;
  flex-wrap: wrap;
}

.notice-detail.mobile {
  width: 100%;
  margin-bottom: 50px;
}

.notice-buttons.mobile {
  flex-direction: row;
  flex-wrap: wrap;
}

.inner.mobile {
  margin: auto;
}

.back-button.mobile {
  display: block;
  margin-bottom: 15px;
}
</style>
