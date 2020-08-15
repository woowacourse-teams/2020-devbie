const validator = {
  member: {
    name: [v => !!v || "닉네임 입력이 필요합니다."],
    email: [
      v => !!v || "이메일 입력이 필요합니다.",
      v => /.+@.+/.test(v) || "유효한 이메일을 입력해주세요"
    ]
  }
};

export default validator;
