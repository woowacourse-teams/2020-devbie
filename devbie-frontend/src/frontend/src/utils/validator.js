import { urlValidator } from "@/utils/noticeUtil";

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
    language: [
      v => !!v || "항목을 선택해주세요.",
      v => v.length !== 0 || "1개이상 선택해주세요."
    ],
    url: [
      v => !!v || "URL 입력이 필요합니다.",
      v => v.trim() !== "" || "공백은 입력이 불가능합니다.",
      v => !!urlValidator(v) || "옳바른 URL을 입력해야합니다."
    ]
  },
  hashtag: {
    input: [
      v =>
        !(
          v.toString().length !== 0 &&
          v
            .toString()
            .split(",")
            .map(value => value.trim())
            .includes("")
        ) || "해시태그는 공백일 수 없습니다."
    ]
  }
};

export default validator;
