<template>
  <v-row align="center" justify="center">
    <v-col cols="12" sm="8" md="4">
      <v-card>
        <v-card-title>
          <span class="headline">회원 정보</span>
        </v-card-title>
        <v-card-subtitle class="subtitle">
          <v-avatar class="avatar" color="primary" size="200">
            <v-img :src="image" alt="avatar-image" />
          </v-avatar>
        </v-card-subtitle>
        <v-card-text>
          <v-text-field label="닉네임*" v-model="name"></v-text-field>
          <v-text-field label="이메일*" v-model="email"></v-text-field>
          <small>*는 필수로 입력해야 합니다</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="resetUpdate">취소</v-btn>
          <v-btn color="blue darken-1" text>저장</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>
<script>
  import {mapGetters} from "vuex";

  export default {
  name: "MyPage",

  data() {
    return {
      email: "",
      name: "",
      image: ""
    };
  },

  computed: {
    ...mapGetters(["fetchedLoginUser"])
  },
  created() {
    this.initializeUserInfo();
  },
  watch: {
    fetchedLoginUser: function() {
      this.initializeUserInfo();
    }
  },
  methods: {
    initializeUserInfo() {
      this.email = this.fetchedLoginUser.email;
      this.name = this.fetchedLoginUser.name;
      this.image = this.fetchedLoginUser.image;
    },
    resetUpdate() {
      this.initializeUserInfo();
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
