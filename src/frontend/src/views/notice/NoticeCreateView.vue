<template>
  <div class="container">
    <v-form ref="form" v-model="valid" lazy-validation class="notice-form">
      <v-select
        item-text="text"
        item-value="value"
        v-model="request.noticeType"
        :items="noticeTypeItems"
        :rules="[v => !!v || 'Item is required']"
        label="채용/교육"
        required
      ></v-select>
      <v-select
        v-model="request.jobPosition"
        :items="jobPositionItems"
        :rules="[v => !!v || 'Item is required']"
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
        <v-text-field
          v-model="request.startDate"
          :rules="textRules"
          label="시작일"
          required
        ></v-text-field>
        <v-text-field
          v-model="request.endDate"
          :rules="textRules"
          label="종료일"
          required
        ></v-text-field>
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
      <v-file-input label="사진" filled v-model="request.image"> </v-file-input>
      <v-textarea
        outlined
        v-model="request.description"
        name="input-7-4"
        label="회사설명"
        value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
      ></v-textarea>

      <v-btn
        :disabled="!valid"
        color="success"
        class="mr-4 submit"
        @click="validate"
      >
        작성하기
      </v-btn>
    </v-form>
  </div>
</template>

<script>
export default {
  data: () => ({
    valid: true,
    numberRules: [
      v => !!v || "숫자를 입력하세요.",
      v => Number.isInteger(Number(v)) || "숫자를 입력해야합니다."
    ],
    textRules: [
      v => !!v || "text is required",
      v => (v && v.length > 0) || "Name must be less than 10 characters"
    ],
    noticeTypeItems: [
      { text: "채용", value: "JOB" },
      { text: "교육", value: "EDUCATION" }
    ],
    jobPositionItems: [
      { text: "백엔드 개발자", value: "BACKEND" },
      { text: "프론트엔드 개발자", value: "FRONTEND" }
    ],
    languageItems: [
      { text: "C", value: "C" },
      { text: "C++", value: "CPP" },
      { text: "JAVA", value: "JAVA" }
    ],
    request: {
      jobPosition: "",
      image: "",
      title: "",
      name: "",
      salary: 0,
      languages: [],
      description: "",
      startDate: "",
      endDate: "",
      noticeType: ""
    }
  }),

  methods: {
    async validate() {
      if (!this.$refs.form.validate()) {
        return;
      }
      try {
        await this.$store.dispatch("CREATE_NOTICE", this.request);
        const id = await this.$store.getters.fetchedNewCreatedNoticeId;
        await this.$router.push(`/notices/${id}`);
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  width: 500px;
  justify-content: center !important;
  align-items: center;
}
.notice-form {
  width: 100%;
  text-align: right;
}
.duration {
  display: flex;
}
.duration:first-child {
  padding-right: 20px;
}
</style>
