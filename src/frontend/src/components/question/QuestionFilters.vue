<template>
  <div class="filters">
    <router-link class="condition" to="/questions">전체 질문</router-link>
    <router-link class="condition" to="/questions?orderBy=VISITS"
      >인기 질문
    </router-link>
    <router-link class="condition" to="/questions?orderBy=RECOMMENDATIONS"
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
  padding-right: 20px;
  border-right: solid 1px #e8e8e8;
  height: 90%;
}

.condition {
  text-decoration: none;
  color: #cfe1e8;
  font-size: 26px;
  margin-bottom: 10px;
}

a.router-link-exact-active {
  color: #35495e;
}
</style>
