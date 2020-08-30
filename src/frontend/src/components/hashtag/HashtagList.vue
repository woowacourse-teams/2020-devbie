<template>
  <div class="inner">
    <v-btn
      class="button control-btn hashtag-list"
      :class="$mq"
      large
      color="#DAEBEA"
      @click.stop="drawer = !drawer"
      >태그 목록
    </v-btn>
    <v-navigation-drawer v-model="drawer" absolute temporary>
      <v-list-item class="drawer-title">
        <v-list-item-content>
          <v-list-item-title>해시 태그 목록</v-list-item-title>
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
          v-for="hashtag in fetchedHashtags"
          :key="hashtag.id"
          @click="$router.push(`/questions?hashtag=${hashtag.tagName}`)"
          ><span>#{{ hashtag.tagName }}</span></v-btn
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

  created() {
    this.$store.dispatch("FETCH_HASHTAGS");
  },

  methods: {
    close() {
      this.drawer = false;
    }
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

.hashtag span {
  font-size: 13px !important;
  font-weight: lighter !important;
  padding-top: 3px;
}

.hashtag:hover {
  background-color: #daebea !important;
}

.hashtag span:hover {
  color: #fc8c84;
}

.control-btn {
  width: 95px;
}

.control-btn.mobile {
  margin: 0 5px;
  width: 60px !important;
  height: 40px !important;
  font-size: 10px !important;
}

.btn:hover {
  cursor: pointer;
}

.drawer-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
