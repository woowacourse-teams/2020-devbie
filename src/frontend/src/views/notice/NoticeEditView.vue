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
<<<<<<< HEAD
      {{ fetchedNotice }}
=======
>>>>>>> 504b607a839cd90d5fbe9b67b1dd6aa986fed727
    </v-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { dateParser, languageTranslator } from "@/util";

export default {
  async created() {
    await this.isAdmin();

    this.id = this.$route.params.id;
    await this.$store.dispatch("FETCH_NOTICE", this.id);

    this.request = {
      title: this.fetchedNotice.title,
      name: this.fetchedNotice.company.name,
      jobPosition: this.fetchedNotice.jobPosition,
      noticeType: this.fetchedNotice.noticeType,
      startDate: dateParser(
        this.fetchedNotice.duration === null
          ? ""
          : this.fetchedNotice.duration.startDate
      ),
      endDate: dateParser(
        this.fetchedNotice.duration === null
          ? ""
          : this.fetchedNotice.duration.endDate
      ),
      description: this.fetchedNotice.noticeDescription.content,
      languages: this.fetchedNotice.noticeDescription.languages.map(language =>
        languageTranslator(language)
      ),
      salary: this.fetchedNotice.company.salary,
      image: this.fetchedNotice.image
    };
  },
  computed: {
    ...mapGetters(["fetchedNotice"])
  },
  data: function() {
    return {
      id: "",
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
      request: {}
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
      try {
        await this.$store.dispatch("EDIT_NOTICE", {
          id: this.id,
          params: this.request
        });
        await this.$router.push(`/notices/${this.id}`);
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
