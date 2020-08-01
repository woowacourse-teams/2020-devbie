export default {
  SET_LOGIN_USER(state, data) {
    state.loginUser = data;
  },
  DELETE_LOGIN_USER(state) {
    state.loginUser = {};
  },
  SET_QUESTIONS(state, data) {
    state.questions = data;
  },
  SET_QUESTION(state, data) {
    state.question = data;
  },
  SET_QUESTION_RECOMMENDATION(state, data) {
    state.questionRecommendation = data;
  },
  SET_MY_QUESTION_RECOMMENDATION(state, data) {
    state.myQuestionRecommendation = data;
  },
  SET_ANSWERS(state, data) {
    state.answers = data;
  },
  SET_ANSWER(state, id, content) {
    state.answers.some(answer => {
      if (answer.id === id) {
        answer.content = content;
        return true;
      }
      return false;
    });
  },
  DELETE_ANSWER(state, id) {
    state.answers = state.answers.filter(answer => answer.id !== id);
  },
  SET_ANSWER_RECOMMENDATION(state, payload) {
    state.answerRecommendation = state.answerRecommendation.filter(
      ar => ar.answerId !== payload.answerId
    );
    state.answerRecommendation.push(payload);
  },
  SET_MY_ANSWER_RECOMMENDATION(state, payload) {
    state.myAnswerRecommendation.push(payload);
  },
  SET_NOTICES(state, data) {
    state.notices = data;
  },
  SET_NEW_QUESTION_ID(state, data) {
    state.questionId = data;
  }
};
