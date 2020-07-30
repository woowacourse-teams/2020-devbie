import {
  createQuestion,
  deleteAnswer,
  deleteQuestion,
  fetchAnswers,
  fetchLoginUser,
  fetchQuestionDetail,
  fetchQuestionList,
  fetchQuestionRecommendation,
  updateAnswer,
  updateQuestion
  createQuestion,
  updateQuestion,
  deleteQuestion,
  fetchNotices
} from "../api";

export default {
  async FETCH_LOGIN_USER({ commit }) {
    try {
      const { data } = await fetchLoginUser();
      commit("SET_LOGIN_USER", data);
    } catch (error) {
      console.log(error);
    }
  },
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
  },
  async UPDATE_QUESTION({ commit }, payload) {
    try {
      await updateQuestion(payload.request, payload.id);
      commit();
    } catch (error) {
      console.log(error);
    }
  },
  async DELETE_QUESTION({ commit }, questionId) {
    try {
      await deleteQuestion(questionId);
      commit();
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
      await deleteAnswer(answerId).then(() => {
        commit("DELETE_ANSWER", answerId);
      });
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
