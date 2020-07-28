import {
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  fetchNotices
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
      console.log(data);
      commit("SET_QUESTION_RECOMMENDATION", data);
    } catch (error) {
      console.log(error);
    }
  },
  async FETCH_NOTICES({ commit }) {
    try {
      const { data } = await fetchNotices();
      commit("SET_NOTICES", data);
    } catch (error) {
      console.log(error);
    }
  }
};
