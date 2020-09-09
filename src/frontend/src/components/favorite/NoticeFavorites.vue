<template>
  <div>
    <v-row dense>
      <div
        v-for="notice in fetchedNoticeFavorites"
        :key="notice.id"
        class="item"
      >
        <v-card class="v-card">
          <v-img
            @click.stop="$router.push(`/notices/${notice.id}`)"
            :src="notice.image"
            class="white--text align-end card-image"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title-text"
              v-text="`${notice.name} - ${notice.title}`"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <v-col cols="12">
              <div
                align="left"
                class="big-font notice-title"
                style="font-weight: bold"
              >
                {{ notice.languages.join(", ") }}
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
    }
  }
};
</script>

<style scoped>
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
.item:last-child {
  margin-right: auto;
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
</style>
