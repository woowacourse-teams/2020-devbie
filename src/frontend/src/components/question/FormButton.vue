<template>
  <div>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
        class="button"
        large
        color="#DAEBEA"
        @click="$router.push(`/questions`)"
      >
        <span>돌아가기</span>
      </v-btn>
      <slot></slot>
    </v-card-actions>
  </div>
</template>

<script>
export default {};
</script>

<style scoped></style>
