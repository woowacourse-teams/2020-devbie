<template>
  <div>
    <v-row dense>
      <v-col
        v-for="notice in fetchedNoticeFavorites"
        :key="notice.id"
        :cols="2"
        class="selector-item"
      >
        <v-card @click.stop="$router.push(`/notices/${notice.id}`)">
          <v-img
            :src="notice.image"
            class="white--text align-end"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="200px"
          >
            <v-card-title
              class="card-title"
              v-text="`${notice.name} - ${notice.title}`"
            ></v-card-title>
          </v-img>

          <v-card-actions class="notice-info">
            <div>
              {{ notice.languages.join(", ") }}
            </div>
            <div>
              {{ notice.jobPosition }}
            </div>
            <favorite-control
              :targetObjectId="notice.id"
              :isUserFavorite="isUserNoticeFavorites(notice.id)"
              :isQuestion="false"
            ></favorite-control>
          </v-card-actions>
        </v-card>
      </v-col>
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

<style scoped></style>
