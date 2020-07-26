import { fetchQuestionList, fetchQuestion } from "@/api";

export default {
  FETCH_QUESTIONS({ commit }) {
    fetchQuestionList()
      .then(response => {
        commit("SET_QUESTIONS", response.data);
        return response;
      })
      .catch(error => console.log(error));
  },
  FETCH_QUESTION({ commit }, id) {
    fetchQuestion(id)
      .then(response => {
        commit("SET_QUESTION", response.data);
        return response;
      })
      .catch(error => console.log(error));
  }
};
