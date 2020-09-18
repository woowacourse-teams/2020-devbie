<template>
  <div>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn
        class="button"
        large
        color="#DAEBEA"
        @click="$router.push(redirectUrl)"
      >
        <span>돌아가기</span>
      </v-btn>
      <slot></slot>
    </v-card-actions>
  </div>
</template>

<script>
export default {
  props: ["editingFlag"],

  computed: {
    redirectUrl() {
      if (this.editingFlag) {
        return `/questions/${this.$route.params.id}`;
      }
      return `/questions?orderBy=CREATED_DATE`;
    }
  }
};
</script>

<style scoped></style>
