<template>
  <div class="hashtag-box">
    <div class="hashtags">
      <button
        class="hashtag-item"
        v-for="(item, index) in items"
        v-bind:key="item.id"
        @click="deleteHashtag(index)"
      >
        #{{ item }}
      </button>
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
      const item = this.tagName.trim().toLowerCase();
      if (item === "") {
        console.log("태그 공백일 수 없습니다.");
        return;
      }
      if (this.items.includes(item)) {
        this.tagName = "";
        return;
      }
      this.items.push(item);
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
  padding: 8px;
  margin-right: 5px;
  margin-bottom: 8px;
  font-family: "Roboto", sans-serif;
  font-size: 11px;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 20px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}

.hashtag-item:hover {
  background-color: #2ee59d;
  box-shadow: 0 15px 20px rgba(46, 229, 157, 0.4);
  color: #fff;
  transform: translateY(-7px);
}
.hashtag-input-text {
  margin-right: 20px;
  min-width: 190px;
}
</style>
