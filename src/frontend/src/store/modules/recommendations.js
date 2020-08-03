import { getAction } from "../../api";

export default {
  state: {
    questionRecommendation: []
  },
  mutations: {
    SET_QUESTION_RECOMMENDATION(state, data) {
      state.questionRecommendation = data;
    }
  },
  actions: {
    async FETCH_QUESTION_RECOMMENDATION({ commit }, questionId) {
      try {
        const { data } = await getAction(
          `/api/recommendation-question?objectId=${questionId}`
        );
        commit("SET_QUESTION_RECOMMENDATION", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedQuestionRecommendation(state) {
      return state.questionRecommendation;
    }
  }
};
