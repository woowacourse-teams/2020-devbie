<template>
  <div class="detail">
    <notice-scroll id="notice-scroll"></notice-scroll>
    <router-view :key="id"></router-view>
  </div>
</template>

<script>
import NoticeScroll from "../../components/notice/NoticeScroll";

export default {
  data() {
    return {
      id: this.$route.params.id
    };
  },
  components: {
    NoticeScroll
  }
};
</script>

<style scoped>
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
