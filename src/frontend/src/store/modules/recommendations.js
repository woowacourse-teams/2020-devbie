import { getAction, putAction, deleteAction } from "../../api";

export default {
  state: {
    questionRecommendation: [],
    myQuestionRecommendation: [],
    answerRecommendation: [],
    myAnswerRecommendation: []
  },
  mutations: {
    SET_QUESTION_RECOMMENDATION(state, data) {
      state.questionRecommendation = data;
    },
    SET_MY_QUESTION_RECOMMENDATION(state, data) {
      state.myQuestionRecommendation = data;
    },
    SET_ANSWER_RECOMMENDATION(state, payload) {
      state.answerRecommendation = state.answerRecommendation.filter(
        ar => ar.answerId !== payload.answerId
      );
      state.answerRecommendation.push(payload);
    },
    SET_MY_ANSWER_RECOMMENDATION(state, payload) {
      state.myAnswerRecommendation.push(payload);
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
    },
    async FETCH_MY_QUESTION_RECOMMENDATION({ commit }, payload) {
      try {
        const { data } = await getAction(
          `/api/recommendation-question?objectId=${payload.questionId}&userId=${payload.userId}`
        );
        commit("SET_MY_QUESTION_RECOMMENDATION", data);
      } catch (error) {
        console.log(error);
      }
    },
    async ON_QUESTION_RECOMMENDATION({ commit }, payload) {
      try {
        const recommendationType = payload.recommendationType;
        await putAction(
          `/api/recommendation-question?objectId=${payload.questionId}`,
          {
            recommendationType: recommendationType
          }
        );
        commit();
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async DELETE_QUESTION_RECOMMENDATION({ commit }, questionId) {
      try {
        await deleteAction(
          `/api/recommendation-question?objectId=${questionId}`
        );
        commit();
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async FETCH_ANSWER_RECOMMENDATION({ commit }, answerId) {
      try {
        const { data } = await getAction(
          `/api/recommendation-answer?objectId=${answerId}`
        );
        const recommendedCount = data.recommendedCount;
        const nonRecommendedCount = data.nonRecommendedCount;
        commit("SET_ANSWER_RECOMMENDATION", {
          answerId,
          recommendedCount,
          nonRecommendedCount
        });
      } catch (error) {
        console.log(error);
      }
    },
    async FETCH_MY_ANSWER_RECOMMENDATION({ commit }, payload) {
      try {
        const { data } = await getAction(
          `/api/recommendation-answer?objectId=${payload.answerId}&userId=${payload.userId}`
        );
        const answerId = payload.answerId;
        const recommendationType = data.recommendationType;
        commit("SET_MY_ANSWER_RECOMMENDATION", {
          answerId,
          recommendationType
        });
      } catch (error) {
        console.log(error);
      }
    },
    async ON_ANSWER_RECOMMENDATION({ commit }, payload) {
      try {
        const recommendationType = payload.recommendationType;
        await putAction(
          `/api/recommendation-answer?objectId=${payload.answerId}`,
          {
            recommendationType: recommendationType
          }
        );
        commit();
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async DELETE_ANSWER_RECOMMENDATION({ commit }, answerId) {
      try {
        await deleteAction(`/api/recommendation-answer?objectId=${answerId}`);
        commit();
      } catch (error) {
        console.log(error);
        throw error;
      }
    }
  },
  getters: {
    fetchedQuestionRecommendation(state) {
      return state.questionRecommendation;
    },
    fetchedMyQuestionRecommendation(state) {
      return state.myQuestionRecommendation;
    },
    fetchedAnswerRecommendation: state => answerId => {
      return state.answerRecommendation.filter(
        ar => ar.answerId === answerId
      )[0];
    },
    fetchedMyAnswerRecommendation: state => answerId => {
      return state.myAnswerRecommendation.filter(
        my => my.answerId === answerId
      )[0];
    }
  }
};
