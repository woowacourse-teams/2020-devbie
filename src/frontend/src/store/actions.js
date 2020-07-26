import { fetchQuestionList } from "@/api";

export default {
  FETCH_QUESTIONS({ commit }) {
    fetchQuestionList()
      .then(response => {
        commit("SET_QUESTIONS", response.data);
        return response;
      })
      .catch(error => console.log(error));
  }
};
