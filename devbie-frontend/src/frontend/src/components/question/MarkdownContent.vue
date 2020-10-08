<template>
  <div>
    <div class="paragraph" v-html="changeToMarkdown"></div>
  </div>
</template>

<script>
import marked from "marked";

export default {
  props: ["content"],

  computed: {
    changeToMarkdown() {
      return marked(this.content);
    }
  }
};
</script>

<style scoped>
.paragraph {
  word-wrap: break-word;
}

.paragraph /deep/ img {
  max-width: 100% !important;
  height: auto !important;
}
</style>
