const validator = {
  member: {
    name: [v => !!v || "닉네임 입력이 필요합니다."],
    email: [
      v => !!v || "이메일 입력이 필요합니다.",
      v => /.+@.+/.test(v) || "유효한 이메일을 입력해주세요"
    ]
  },
  notice: {
    text: [
      v => !!v || "텍스트 입력이 필요합니다.",
      v => v.trim() !== "" || "공백은 입력이 불가능합니다."
    ],
    selected: [v => !!v || "항목을 선택해주세요."],
    salary: [
      v => !!v || "입력이 필요합니다.",
      v => parseInt(v) || "숫자만 입력 가능합니다.",
      v => Number(v) > 0 || "양수만 입력가능합니다."
    ],
    language: [
      v => !!v || "항목을 선택해주세요.",
      v => v.length !== 0 || "1개이상 선택해주세요."
    ]
  }
};

export default validator;
