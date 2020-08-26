<template>
  <div class="container">
    <v-form ref="form" lazy-validation class="notice-form">
      <v-select
        item-text="text"
        item-value="key"
        v-model="request.noticeType"
        :items="noticeTypeItems"
        label="채용/교육"
        :rules="rules.selected"
      ></v-select>
      <v-select
        v-model="request.jobPosition"
        item-text="text"
        item-value="key"
        :items="fetchedJobPositions"
        label="직군"
        :rules="rules.selected"
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
        :rules="rules.language"
      ></v-select>
      <v-text-field
        v-model="request.title"
        label="공고 이름"
        :rules="rules.text"
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
        label="회사이름"
        required
        :rules="rules.text"
      ></v-text-field>
      <v-text-field
        v-model="request.salary"
        label="연봉"
        required
        :rules="rules.salary"
      ></v-text-field>
      <input type="file" ref="image" @change="imageUpload" />
      <v-textarea
        outlined
        v-model="request.description"
        name="input-7-4"
        label="회사설명"
        :rules="rules.text"
      ></v-textarea>
      <v-btn color="success" class="mr-4 submit" @click="submit">
        작성하기
      </v-btn>
    </v-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import validator from "../../utils/validator";

export default {
  data() {
    return {
      rules: { ...validator.notice },
      noticeTypeItems: [
        { text: "채용", key: "JOB" },
        { text: "교육", key: "EDUCATION" }
      ],
      title: "",
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

  computed: {
    ...mapGetters(["fetchedLanguages", "fetchedJobPositions"])
  },

  created() {
    this.checkAdmin();
    this.$store.dispatch("FETCH_FILTERS");
  },

  methods: {
    async checkAdmin() {
      const fetchedLoginUser = await this.$store.getters.fetchedLoginUser;
      if (fetchedLoginUser === null || fetchedLoginUser.roleType !== "ADMIN") {
        await this.$router.push("/");
      }
    },
    async imageUpload() {
      const image_files = this.$refs.image.files[0];
      if (!image_files) {
        return;
      }

      const formData = new FormData();
      formData.append("image", image_files);

      try {
        this.request.image = await this.$store.dispatch(
          "UPLOAD_NOTICE_IMAGE",
          formData
        );
      } catch (e) {
        console.error(e);
      }
    },
    async submit() {
      if (!this.$refs.form.validate()) {
        return;
      }

      this.request.image =
        this.request.image === ""
          ? "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
          : this.request.image;

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
