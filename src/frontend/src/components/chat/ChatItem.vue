<template>
  <div>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title>
          {{ chat.name }}
        </v-list-item-title>
      </v-list-item-content>
      <v-list-item-content>
        {{ chat.message }}
      </v-list-item-content>
    </v-list-item>
  </div>
</template>

<script>
export default {
  props: {
    chat: {
      type: Object,
      required: true
    }
  }
};
</script>

<style scoped></style>
