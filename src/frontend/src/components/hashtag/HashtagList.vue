<template>
  <div class="inner">
    <v-btn
      class="button control-btn hashtag-list"
      large
      color="#DAEBEA"
      @click.stop="drawer = !drawer"
      >태그 목록
    </v-btn>
    <v-navigation-drawer right v-model="drawer" absolute temporary>
      <v-list-item class="drawer-title">
        <v-list-item-content>
          <v-list-item-title>태그 목록</v-list-item-title>
        </v-list-item-content>
        <i
          @click="drawer = false"
          class="fa fa-times btn"
          aria-hidden="true"
        ></i>
      </v-list-item>

      <v-divider></v-divider>
      <div class="hashtag-list">
        <v-btn
          small
          rounded
          color="#E8E8E8"
          class="hashtag button"
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
    close() {
      this.drawer = false;
    }
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
  font-size: 6px !important;
  margin: 4px;
  padding: 7px !important;
}

.hashtag:hover {
  background-color: #daebea !important;
}

.control-btn {
  width: 95px;
}

.btn:hover {
  cursor: pointer;
  color: #fc8c84;
}

.drawer-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
