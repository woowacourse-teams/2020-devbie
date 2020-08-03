<template>
  <div class="detail">
    <div class="left-menu"></div>
    <notice-detail id="notice-detail"></notice-detail>
  </div>
</template>

<script>
import NoticeDetail from "../components/notice/NoticeDetail";

export default {
  components: {
    NoticeDetail
  }
};
</script>

<style scoped>
.detail {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.left-menu {
  flex-grow: 1;
  border-right: solid 1px #e8e8e8;
}
#notice-detail {
  flex-grow: 8;
}
</style>
