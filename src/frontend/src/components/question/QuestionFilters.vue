<template>
  <div class="filters">
    <router-link class="condition" :to="`/questions`">전체 질문</router-link>
    <router-link class="condition" :to="`questions22`">인기 질문</router-link>
    <router-link class="condition" :to="`/questions?orderBy=actual`"
      >면접에 나온 질문</router-link
    >
    <router-link class="condition" :to="`/questions?orderBy=expected`"
      >면접 예상 질문</router-link
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
  margin-top: 25px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-right: 20px;
  border-right: solid 1px #e8e8e8;
}
.condition {
  text-decoration: none;
  color: #cfe1e8;
  font-size: 24px;
  margin-bottom: 10px;
}
a.router-link-exact-active {
  color: #35495e;
}
</style>
