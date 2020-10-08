<template>
  <div
    class="notice-favorite-list"
    :class="$mq"
    style="margin: 50px 150px 50px 150px"
  >
    <div class="filters">
      <favorite-type :isNoticeFavorite="true"></favorite-type>
    </div>
    <notice-favorites></notice-favorites>
  </div>
</template>

<script>
import NoticeFavorites from "../../components/favorite/NoticeFavorites";
import FavoriteType from "../../components/favorite/FavoriteType";

export default {
  components: {
    FavoriteType,
    NoticeFavorites
  }
};
</script>

<style scoped>
.filters {
  display: flex;
  align-content: center;
  justify-content: left;
  margin-bottom: 50px;
}

.filters > * {
  margin-right: 120px;
}

.notice-favorite-list.mobile {
  margin: 0 !important;
}
</style>
