<template>
  <div class="inner">
    <v-btn
      class="control-btn hashtag-list"
      color="#DAEBEA"
      @click.stop="drawer = !drawer"
      >태그 목록
    </v-btn>
    <v-navigation-drawer right v-model="drawer" absolute temporary>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>태그 목록</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>
      <div class="hashtag-list">
        <v-btn
          small
          color="#E8E8E8"
          class="hashtag"
          v-for="hashtag in fetchedHashtags.hashtags"
          :key="hashtag.id"
          @click="$router.push(`/questions?hashtag=${hashtag.tagName}`)"
          >#{{ hashtag.tagName }}</v-btn
        >
      </div>
    </v-navigation-drawer>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      drawer: null
    };
  },
  computed: {
    ...mapGetters(["fetchedHashtags"])
  },
  methods: {
    searchByHashtag() {}
  },
  created() {
    this.$store.dispatch("FETCH_HASHTAGS");
  }
};
</script>

<style scoped>
.hashtag-list {
  padding: 9px;
  display: flex;
  align-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.hashtag {
  margin: 4px;
  padding: 7px !important;
}

.hashtag:hover {
  color: darkorange;
  text-decoration: underline;
}

.control-btn {
  width: 95px;
}
</style>
