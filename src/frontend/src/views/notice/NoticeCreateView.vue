<template>
  <div><notice-create></notice-create></div>
</template>

<script>
import NoticeCreate from "../../components/notice/NoticeCreate";

export default {
  components: {
    NoticeCreate
  }
};
</script>

<style scoped></style>
<template>
  <div class="container">
    <v-form ref="form" v-model="valid" lazy-validation class="notice-form">
      <v-select
        item-text="text"
        item-value="value"
        v-model="request.noticeType"
        :items="noticeTypeItems"
        :rules="selectRules"
        label="채용/교육"
        required
      ></v-select>
      <v-select
        v-model="request.jobPosition"
        :items="jobPositionItems"
        :rules="selectRules"
        label="직군"
        required
      ></v-select>
      <v-select
        v-model="request.languages"
        :items="languageItems"
        attach
        chips
        label="프로그래밍 언어"
        multiple
      ></v-select>
      <v-text-field
        v-model="request.title"
        :rules="textRules"
        label="공고 이름"
        required
      ></v-text-field>
      <div class="duration">
        <input
          aria-label="시작일"
          v-model="request.startDate"
          type="datetime-local"
        />
        <input
          aria-labelledby="종료일"
          v-model="request.endDate"
          type="datetime-local"
        />
      </div>
      <v-text-field
        v-model="request.name"
        :rules="textRules"
        label="회사이름"
        required
      ></v-text-field>
      <v-text-field
        v-model="request.salary"
        :rules="numberRules"
        label="연봉"
        required
      ></v-text-field>
      <input type="file" ref="image" @change="imageUpload" />
      <v-textarea
        outlined
        v-model="request.description"
        name="input-7-4"
        label="회사설명"
      ></v-textarea>
      <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4 submit"
        @click="submit"
      >
        작성하기
      </v-btn>
    </v-form>
  </div>
</template>
