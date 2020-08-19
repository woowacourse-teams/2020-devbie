<template>
  <div style="height:100%" class="d-flex flex-column">
    <div>
      익명의 이름
    </div>
    <div>
      <v-text-field
        label="Solo"
        placeholder="욕설은 삼가해주세요"
        solo
        background-color="grey"
        height="100px"
      ></v-text-field>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatInput"
};
</script>

<style scoped></style>
