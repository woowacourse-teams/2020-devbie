<script>
import axios from "axios";
export default {
  async created() {
    const code = this.$route.query.code;

    const response = await axios.post("/api/oauth/login?code=" + code);
    const { token } = await response.data;

    localStorage.setItem("devbieToken", token);
    window.location.href = "/";
  }
};
</script>
