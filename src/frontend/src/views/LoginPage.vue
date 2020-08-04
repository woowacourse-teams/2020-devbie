<script>
import axios from "axios";
export default {
  async created() {
    const code = this.$route.query.code;
    // code 존재 여부 확인 후 추가 정보 입력 폼 띄우기
    const response = await axios.post("/api/auth/login?code=" + code);
    const { token } = await response.data;

    localStorage.setItem("devbieToken", token);
    window.location.href = "/";
  }
};
</script>
