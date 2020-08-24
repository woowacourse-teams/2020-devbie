<template>
  <div class="notice-fovorite-list" style="margin: 50px 150px 50px 150px">
    <div class="filters">
      <favorite-type :isQuestionFavorite="true"></favorite-type>
    </div>
    <div class="interview">
      <div class="inner">
        <question-favorites id="question-list"></question-favorites>
      </div>
    </div>
  </div>
</template>

<script>
import QuestionFavorites from "../../components/favorite/QuestionFavorites";
import FavoriteType from "../../components/favorite/FavoriteType";

export default {
  components: {
    QuestionFavorites,
    FavoriteType
  }
};
</script>

<style scoped>
.interview {
  display: flex;
  justify-content: center;
  margin: 0;
}

.inner {
  width: 95%;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

.filters {
  display: flex;
  align-content: center;
  justify-content: left;
  margin-bottom: 50px;
}

.filters > * {
  margin-right: 120px;
}

#question-list {
  flex-grow: 10;
}
</style>
