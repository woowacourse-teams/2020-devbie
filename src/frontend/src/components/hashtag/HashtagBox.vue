<template>
  <div class="hashtag-box">
    <v-combobox
      v-model="items"
      :items="allHashtags"
      label="#해시태그 추가"
      item-text="tagName"
      dense
      multiple
      chips
      clearable
      clear-icon="fas fa-trash"
      :rules="rules.input"
    >
      <template v-slot:selection="data">
        <v-chip
          class="hashtag-item"
          :key="JSON.stringify(data.item)"
          v-bind="data.attrs"
          :input-value="data.selected"
          :disabled="data.disabled"
          close
          @click:close="data.parent.selectItem(data.item)"
        >
          #{{ changeToLowerCase(data.item) }}
        </v-chip>
      </template>
      <template v-slot:item="data">
        <v-list-item-content>
          <v-list-item-title v-html="data.item"></v-list-item-title>
        </v-list-item-content>
      </template>
    </v-combobox>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import validator from "../../utils/validator";

export default {
  props: ["existHashtags"],

  data() {
    return {
      search: "",
      allHashtags: [],
      items: [],
      rules: { ...validator.hashtag }
    };
  },

  computed: {
    ...mapGetters(["fetchedHashtags"]),

    extractTagNames() {
      return this.fetchedHashtags.map(h => h.tagName);
    }
  },

  watch: {
    items() {
      this.$emit("hashtags", this.items);
    },

    existHashtags() {
      this.items = this.existHashtags;
    }
  },

  async created() {
    await this.$store.dispatch("FETCH_HASHTAGS");
    this.allHashtags = this.extractTagNames;
  },

  methods: {
    changeToLowerCase(value) {
      return value
        .toString()
        .trim()
        .toLowerCase();
    }
  }
};
</script>

<style scoped>
.hashtag-box {
  display: flex;
  align-items: center;
}

.hashtag-item {
  background-color: #daebea !important;
  display: flex !important;
  align-items: center !important;
  margin-top: 4px !important;
  margin-bottom: 2px !important;
}

.hashtag-item button {
  color: red !important;
}

.hashtag-item:hover {
  color: #fc8c84;
  font-weight: bold;
}
</style>
