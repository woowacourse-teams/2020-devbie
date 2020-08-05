<template>
  <div class="hashtag-box">
    <div class="hashtags">기존의 해시태그</div>
    <div class="input-box">
      <v-text-field
        label="새로운 태그 생성"
        class="input hashtag-input-text"
      ></v-text-field>
      <v-btn class="create-btn">태그 생성</v-btn>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: ["java", "network", "rest"]
    };
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

.hashtag-input-text {
  margin-right: 20px;
  min-width: 190px;
}

.create-btn {
  width: 50px;
}
</style>
