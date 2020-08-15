<template>
  <div class="container">
    <v-form ref="form" v-model="valid" lazy-validation class="notice-form">
      <v-select
        item-text="text"
        item-value="key"
        v-model="request.noticeType"
        :items="noticeTypeItems"
        :rules="selectRules"
        label="채용/교육"
        required
      ></v-select>
      <v-select
        v-model="request.jobPosition"
        item-text="text"
        item-value="key"
        :items="fetchedJobPositions"
        :rules="selectRules"
        label="직군"
        required
      ></v-select>
      <v-select
        item-text="text"
        item-value="key"
        v-model="request.languages"
        :items="fetchedLanguages"
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
      <v-file-input label="사진" filled v-model="request.image"> </v-file-input>
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
        @click="validate"
      >
        작성하기
      </v-btn>
    </v-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  created() {
    this.isAdmin();
    this.$store.dispatch("FETCH_LANGUAGES");
    this.$store.dispatch("FETCH_JOB_POSITIONS");
  },
  computed: {
    ...mapGetters(["fetchedLanguages", "fetchedJobPositions"])
  },
  data: function() {
    return {
      valid: true,
      selectRules: [v => !!v || "Item is required"],
      numberRules: [
        v => !!v || "숫자를 입력하세요.",
        v => Number.isInteger(Number(v)) || "숫자를 입력해야합니다."
      ],
      textRules: [
        v => !!v || "문자를 입력하세요!",
        v => (v && v.length > 0) || "문자를 1자이상 입력해주세요!"
      ],
      noticeTypeItems: [
        { text: "채용", key: "JOB" },
        { text: "교육", key: "EDUCATION" }
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
    };
  },
  methods: {
    async isAdmin() {
      const fetchedLoginUser = await this.$store.getters.fetchedLoginUser;
      if (fetchedLoginUser === null || fetchedLoginUser.roleType !== "ADMIN") {
        await this.$router.push("/");
      }
    },
    async validate() {
      if (!this.$refs.form.validate()) {
        return;
      }
      console.log(this.request);
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
.duration > input {
  margin-right: 20px;
}
.duration:first-child {
  padding-right: 20px;
}
</style>
