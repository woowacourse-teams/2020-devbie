<template>
  <div class="hashtag-box">
    <div class="hashtags">
      <v-btn
        class="hashtag-item"
        x-small
        v-for="(item, index) in items"
        v-bind:key="item.id"
        @click="deleteHashtag(index)"
      >
        #{{ item }}
      </v-btn>
    </div>
    <div class="input-box">
      <v-text-field
        label="#해시태그"
        class="input hashtag-input-text"
        placeholder="Enter로 태그 추가"
        v-model="tagName"
        @keydown.enter="addHashtag"
      ></v-text-field>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      tagName: "",
      items: []
    };
  },
  computed: {
    ...mapGetters(["fetchedQuestion"]),
    tagNames() {
      return (
        this.fetchedQuestion &&
        this.fetchedQuestion.hashtags.map(h => h.tagName)
      );
    }
  },
  watch: {
    fetchedQuestion: function() {
      this.items = this.tagNames;
    },
    items: function() {
      this.$emit("hashtags", this.items);
      this.tagName = "";
    }
  },
  methods: {
    addHashtag() {
      if (this.tagName.trim() === "") {
        console.log("태그 공백일 수 없습니다.");
        return;
      }
      this.items.push(this.tagName);
    },
    deleteHashtag(index) {
      this.items.splice(index, 1);
    }
  }
};
</script>

<style scoped>
.input-box {
  display: flex;
  align-items: center;
}

.input {
  margin-left: 10px;
}

.hashtags {
  display: flex;
  justify-items: center;
  flex-wrap: wrap;
}

.hashtag-item {
  margin-right: 5px;
  margin-bottom: 4px;
}

.hashtag-item:hover {
  color: darkorange;
  text-decoration: underline;
}

.hashtag-input-text {
  margin-right: 20px;
  min-width: 190px;
}
</style>
