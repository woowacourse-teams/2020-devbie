import {
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  fetchAnswers,
  updateAnswer,
  deleteAnswer
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
  async FETCH_ANSWERS({ commit }, questionId) {
    try {
      const { data } = await fetchAnswers(questionId);
      commit("SET_ANSWERS", data.answerResponses);
    } catch (error) {
      console.log(error);
    }
  },
  async UPDATE_ANSWER({ commit }, answerId, content) {
    try {
      await updateAnswer(answerId, content);
      commit("SET_ANSWER", answerId, content);
    } catch (error) {
      console.log(error);
    }
  },
  async DELETE_ANSWER({ commit }, answerId) {
    try {
      await deleteAnswer(answerId);
      commit("DELETE_ANSWER", answerId);
    } catch (error) {
      console.log(error);
    }
  }
};
