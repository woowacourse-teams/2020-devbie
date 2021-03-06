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
        item-text="text"
        item-value="key"
        v-model="request.jobPosition"
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
      <v-radio-group row mandatory v-model="recruitmentState">
        <v-radio label="상시채용"></v-radio>
        <v-radio label="공개채용"></v-radio>
      </v-radio-group>
      <div class="duration" v-if="isPublicRecruitment()">
        <input aria-label="시작일" v-model="request.startDate" type="date" />
        <input aria-labelledby="종료일" v-model="request.endDate" type="date" />
      </div>
      <v-text-field
        v-model="request.name"
        label="회사이름"
        :rules="rules.text"
      ></v-text-field>
      <v-text-field
        v-model="request.applyUrl"
        label="지원 URL"
        :rules="rules.url"
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
import { languageTranslator } from "@/utils/noticeUtil";
import validator from "../../utils/validator";
import { getAction, patchAction } from "@/api";

export default {
  data() {
    return {
      recruitmentState: 0,
      notice: {},
      rules: { ...validator.notice },
      id: "",
      noticeTypeItems: [
        { text: "채용", key: "JOB" },
        { text: "교육", key: "EDUCATION" }
      ],
      request: {}
    };
  },

  computed: {
    ...mapGetters(["fetchedLanguages", "fetchedJobPositions"])
  },

  async created() {
    await this.checkAdmin();
    await this.$store.dispatch("FETCH_FILTERS");

    this.id = this.$route.params.id;
    try {
      const { data } = await getAction(`/api/notices/${this.id}`);
      this.notice = data;
    } catch (error) {
      console.log("공고 불러오기 실패 " + error.response.data.message);
      await this.$store.dispatch(
        "UPDATE_SNACKBAR_TEXT",
        "공고를 불러오지 못했습니다."
      );
    }

    this.request = await this.parameterInitialize();
    this.recruitmentState =
      this.request.duration.recruitmentType === "0" ? "ANY" : "OPEN";
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
      try {
        this.request.recruitmentType =
          this.recruitmentState === 0 ? "ANY" : "OPEN";

        await patchAction(`/api/notices/${this.id}`, this.request);

        await this.$router.push(
          `/notices/${this.request.noticeType}/${this.id}`
        );
      } catch (error) {
        console.log(error);
      }
    },
    parameterInitialize() {
      const duration = this.notice.duration || "";
      return {
        title: this.notice.title,
        name: this.notice.company.name,
        jobPosition: this.notice.jobPosition,
        noticeType: this.notice.noticeType,
        startDate: duration.startDate || null,
        endDate: duration.endDate || null,
        description: this.notice.noticeDescription.content,
        languages: this.notice.noticeDescription.languages.map(language =>
          languageTranslator(language)
        ),
        image: this.notice.image,
        applyUrl: this.notice.noticeDescription.applyUrl,
        recruitmentType: duration.recruitmentType || "ANY"
      };
    },
    isPublicRecruitment() {
      return this.recruitmentState === 1;
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  width: 500px;
  justify-content: center;
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
