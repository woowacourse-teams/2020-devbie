<template>
  <div>
    <v-row dense class="list-parent" :class="$mq">
      <div
        v-for="notice in fetchedNoticeFavorites"
        :key="notice.id"
        class="item"
        :class="$mq"
      >
        <v-card>
          <v-img
            @click="
              $router.push(`/notices/${noticeType || 'JOB'}/${notice.id}`)
            "
            :src="notice.image"
            class="white--text align-end card-image"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title-text"
              v-html="addHighlight(sliceText(notice.name, 20))"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <v-col cols="12">
              <div
                align="left"
                class="big-font notice-title"
                style="font-weight: bold"
              >
                <p v-html="addHighlight(sliceText(notice.title, 40))"></p>
              </div>
              <div class="medium-font">
                언어 : {{ notice.languages.join(", ") }}
              </div>
              <div class="medium-font">포지션 : {{ notice.jobPosition }}</div>
              <favorite-control
                class="heart-icon"
                :targetObjectId="notice.id"
                :isUserFavorite="isUserNoticeFavorites(notice.id)"
                :isQuestion="false"
              ></favorite-control>
            </v-col>
          </v-card-actions>
        </v-card>
      </div>
    </v-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import FavoriteControl from "./FavoriteControl";

export default {
  components: { FavoriteControl },

  props: ["noticeType"],

  computed: {
    ...mapGetters([
      "isLoggedIn",
      "fetchedLoginUser",
      "isUserNoticeFavorites",
      "fetchedNoticeFavorites"
    ])
  },

  watch: {
    isLoggedIn() {
      this.initFavoriteState();
    }
  },

  created() {
    if (this.isLoggedIn) {
      this.initFavoriteState();
    }
  },

  methods: {
    async initFavoriteState() {
      await this.$store.dispatch("FETCH_LOGIN_USER");
      await this.$store.dispatch("FETCH_MY_FAVORITES", {
        userId: this.fetchedLoginUser.id,
        object: "notice"
      });
    },

    sliceText(text, maximumLength) {
      if (text.length > maximumLength) {
        return text.substr(0, maximumLength) + "...";
      }
      return text;
    },

    addHighlight(text) {
      if (this.keyword === undefined || this.keyword === "") {
        return text;
      }

      const regex = new RegExp(this.keyword, "g");
      const highlightingText = text.replace(
        regex,
        `<span style="background: #f1c40f">` + this.keyword + "</span>"
      );

      return highlightingText;
    }
  }
};
</script>

<style scoped>
.v-card {
  height: 100%;
}

.big-font {
  font-size: 17px;
}

.medium-font {
  font-size: 13px;
}

.item {
  width: 22%;
  margin: 0 30px 50px 0;
}

.card-image {
  width: 100%;
}

.card-image:hover {
  opacity: 0.6;
}

.card-title-text {
  justify-content: center;
  color: white;
  max-height: 40px;
  background-color: rgba(0, 0, 0, 0.6);
  flex-wrap: nowrap;
}

.notice-title {
  font-weight: bold;
  padding-bottom: 10px;
}

.heart-icon {
  position: absolute;
  right: 15px;
  bottom: 15px;
}

.list-parent {
  justify-content: center;
  margin: 0;
  padding-left: 2%;
}

.list-parent.mobile {
  width: 80%;
  margin: auto;
}

.item.mobile {
  min-width: 100%;
}

.notice-info {
  width: 100%;
}
</style>
