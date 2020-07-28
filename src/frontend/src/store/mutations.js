export default {
  SET_QUESTIONS(state, data) {
    state.questions = data;
  },
  SET_QUESTION(state, data) {
    state.question = data;
  },
  SET_QUESTION_RECOMMENDATION(state, data) {
    state.questionRecommendation = data;
  }
};
