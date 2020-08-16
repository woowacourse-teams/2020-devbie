<template>
  <div>
    <div v-html="changeToMarkdown"></div>
  </div>
</template>

<script>
import marked from "marked";
import markedImages from "marked-images";

export default {
  props: ["content"],
  computed: {
    changeToMarkdown() {
      return marked(this.content);
    }
  },
  created() {
    const opts = {
      xhtml: false,
      fqImages: { route: "/images/", url: "https://images.example.com" },
      fqLinks: "https://www.example.com",
      relPath: false
    };
    marked.use(markedImages(opts));
  }
};
</script>

<style scoped></style>
