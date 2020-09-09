<template>
  <div>
    <h2 class="title">
      <slot></slot>
    </h2>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.title {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
