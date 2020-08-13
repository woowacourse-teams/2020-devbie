<template>
  <v-row align="center" justify="center">
    <v-col cols="12" sm="8" md="4">
      <v-card>
        <v-card-title>
          <span class="headline">회원 정보</span>
        </v-card-title>
        <v-card-subtitle class="subtitle">
          <v-avatar class="avatar" size="200" @click="editImage">
            <v-img :src="image" alt="avatar-image" />
          </v-avatar>
          <input
            type="file"
            ref="avatar"
            @change="preview"
            accept="image/*"
            style="display:none"
          />
        </v-card-subtitle>
        <v-card-text>
          <v-text-field
            label="닉네임*"
            v-model="name"
            dense
            outlined
            prepend-inner-icon="mdi-account"
            :rules="rules.member.name"
          ></v-text-field>
          <v-text-field
            label="이메일*"
            v-model="email"
            dense
            outlined
            prepend-inner-icon="mdi-email"
            :rules="rules.member.email"
          ></v-text-field>
          <small>*는 필수로 입력해야 합니다</small>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="resetUpdate">취소</v-btn>
          <v-btn color="blue darken-1" text @click="updateUserInfo">저장</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>
<script>
import { mapGetters } from "vuex";
import validator from "../../utils/validator";
import router from "../../router";

export default {
  name: "MyPage",

  data() {
    return {
      id: "",
      email: "",
      name: "",
      image: "",
      rules: { ...validator }
    };
  },
  computed: {
    ...mapGetters(["fetchedLoginUser", "isLoggedIn"])
  },
  async created() {
    await this.$store.dispatch("FETCH_LOGIN_USER");
    this.initializeUserInfo();
    this.checkLoggedIn();
  },
  watch: {
    isLoggedIn() {
      this.checkLoggedIn();
    },

    fetchedLoginUser() {
      this.initializeUserInfo();
    }
  },
  methods: {
    checkLoggedIn() {
      if (!this.isLoggedIn) {
        router.push("/");
      }
    },
    editImage() {
      this.$refs.avatar.click();
    },

    async preview() {
      const image_files = this.$refs.avatar.files[0];
      if (image_files) {
        const reader = new FileReader();

        reader.onload = e => {
          this.image = e.target.result;
        };

        reader.readAsDataURL(image_files);
        await this.updateUserImage(image_files);
      }
    },

    initializeUserInfo() {
      this.id = this.fetchedLoginUser.id;
      this.email = this.fetchedLoginUser.email;
      this.name = this.fetchedLoginUser.name;
      this.image = this.fetchedLoginUser.image;
    },

    resetUpdate() {
      this.initializeUserInfo();
    },

    async updateUserInfo() {
      const updated_info = {
        name: this.name,
        email: this.email
      };

      await this.$store.dispatch("UPDATE_USER_INFO", updated_info);
      this.$store.dispatch("FETCH_LOGIN_USER");
    },

    async updateUserImage(image_files) {
      const formData = new FormData();
      formData.append("image", image_files);
      try {
        await this.$store.dispatch("UPDATE_USER_IMAGE", formData);
      } catch (e) {
        console.error(e);
      } finally {
        this.$store.dispatch("FETCH_LOGIN_USER");
      }
    }
  }
};
</script>

<style scoped>
.subtitle {
  display: flex;
  justify-content: center;
}
</style>
