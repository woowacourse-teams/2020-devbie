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
    <v-text-field
      label="해시태그"
      class="input hashtag-input-text"
      placeholder="Enter로 태그 추가"
      v-model="tagName"
      @keydown.enter="addHashtag"
    ></v-text-field>
  </div>
</template>

<script>
export default {
  props: ["existHashtags"],
  data() {
    return {
      tagName: "",
      items: []
    };
  },
  watch: {
    items() {
      this.$emit("hashtags", this.items);
      this.tagName = "";
    },
    existHashtags() {
      this.items = this.existHashtags;
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
.hashtag-box {
  display: flex;
  align-items: center;
}

.hashtags {
  display: flex;
  justify-items: center;
  flex-wrap: wrap;
  flex-grow: 1;
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
  flex-grow: 1;
  max-width: 50%;
  min-width: 50%;
}
</style>
