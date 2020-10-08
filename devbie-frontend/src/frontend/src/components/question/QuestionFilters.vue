<template>
  <div class="filters" :class="$mq">
    <router-link class="condition" :class="$mq" to="/questions"
      >전체 질문</router-link
    >
    <router-link class="condition" :class="$mq" to="/questions?orderBy=VISITS"
      >인기 질문
    </router-link>
    <router-link
      class="condition"
      :class="$mq"
      to="/questions?orderBy=RECOMMENDATIONS"
      >추천 질문</router-link
    >
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
a:hover {
  color: #9fd0d4;
  text-decoration: underline;
}

.filters {
  font-family: "Jua", sans-serif;
  margin-top: 25px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  height: 90%;
}

.filters.mobile {
  padding: 0;
  margin-bottom: 5px;
}

.condition {
  text-decoration: none;
  color: #cfe1e8;
  font-size: 26px;
  margin-bottom: 10px;
}

.condition.mobile {
  margin: 0 7px;
  font-size: 19px !important;
}

a.router-link-exact-active {
  color: #35495e;
}
</style>
