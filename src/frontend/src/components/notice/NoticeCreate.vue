<template>
  <div class="container">
    <v-form ref="form" lazy-validation class="notice-form">
      <v-card class="mx-auto" max-width="434" tile @click="editImage">
        <v-img :src="image" width="300px" height="200px" alt="notice-image">
          <input
            type="file"
            ref="image"
            @change="preview"
            accept="image/*"
            style="display:none"
          />
          <v-row align="end" class="fill-height">
            <v-col align-self="start" class="pa-0" cols="12"> </v-col>
            <v-col class="py-0">
              <v-list-item color="rgba(0, 0, 0, .4)" dark>
                <v-list-item-content>
                  <v-list-item-title class="title"
                    >공고 이미지 (클릭하여 추가하세요)</v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
            </v-col>
          </v-row>
        </v-img>
      </v-card>
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
import { postAction } from "@/api";

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
      },
      image:
        "https://images.velog.io/images/sonypark/post/55f4b858-ea9e-4348-ba47-88c2310f8f6c/devie.png"
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
    editImage() {
      this.$refs.image.click();
    },

    async preview() {
      const image_files = this.$refs.image.files[0];
      if (image_files) {
        const reader = new FileReader();

        reader.onload = e => {
          this.image = e.target.result;
        };

        reader.readAsDataURL(image_files);
        await this.imageUpload(image_files);
      }
    },

    async checkAdmin() {
      const fetchedLoginUser = await this.$store.getters.fetchedLoginUser;
      if (fetchedLoginUser === null || fetchedLoginUser.roleType !== "ADMIN") {
        await this.$router.push("/");
      }
    },
    async imageUpload(image_files) {
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
        const { headers } = await postAction(`/api/notices`, this.request);
        const nextId = headers.location.split("/")[3];
        await this.$router.push(
          `/notices/${this.request.noticeType}/${nextId}`
        );
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
