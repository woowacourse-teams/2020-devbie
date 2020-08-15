<template>
  <div>
    <hashtag-list id="hashtag-list"></hashtag-list>
  </div>
</template>

<script>
import HashtagList from "../../components/hashtag/HashtagList";

export default {
  components: {
    HashtagList
  }
};
</script>

<style scoped>
#hashtag-list {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
