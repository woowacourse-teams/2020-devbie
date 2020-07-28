import {
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  createQuestion
} from "../api";

export default {
  async FETCH_QUESTIONS({ commit }) {
    try {
      const { data } = await fetchQuestionList();
      commit("SET_QUESTIONS", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_QUESTION({ commit }, id) {
    try {
      const { data } = await fetchQuestionDetail(id);
      commit("SET_QUESTION", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_QUESTION_RECOMMENDATION({ commit }, id) {
    try {
      const { data } = await fetchQuestionRecommendation(id);
      commit("SET_QUESTION_RECOMMENDATION", data);
    } catch (error) {
      console.log(error);
    }
  },
  async CREATE_QUESTION({ commit }, request) {
    try {
      const response = await createQuestion(request);
      const id = response["headers"].location.split("/")[3];
      commit("SET_NEW_QUESTION_ID", id);
    } catch (error) {
      console.log(error);
    }
  }
};
