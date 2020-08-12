import { getAction, putAction, deleteAction } from "../../api";

export default {
  state: {
    myQuestionRecommendation: {},
    myAnswerRecommendation: []
  },
  mutations: {
    SET_MY_QUESTION_RECOMMENDATION(state, data) {
      state.myQuestionRecommendation = data;
    },
    SET_MY_ANSWER_RECOMMENDATION(state, payload) {
      state.myAnswerRecommendation.push(payload);
    },
    UPDATE_MY_ANSWER_RECOMMENDATION(state, payload) {
      state.myAnswerRecommendation.some(answer => {
        if (answer.answerId === payload.answerId) {
          answer.recommendationType = payload.recommendationType;
          return true;
        }
        return false;
      });
    }
  },
  actions: {
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
        await putAction(
          `/api/recommendation-question?objectId=${payload.questionId}`,
          {
            recommendationType: payload.recommendationType
          }
        );
        commit("SET_MY_QUESTION_RECOMMENDATION", {
          recommendationType: payload.recommendationType
        });
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
        commit("SET_MY_QUESTION_RECOMMENDATION", {
          recommendationType: "NOT_EXIST"
        });
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async FETCH_MY_ANSWER_RECOMMENDATION({ commit }, payload) {
      try {
        const { data } = await getAction(
          `/api/recommendation-answer?objectId=${payload.answerId}&userId=${payload.userId}`
        );
        commit("SET_MY_ANSWER_RECOMMENDATION", {
          answerId: payload.answerId,
          recommendationType: data.recommendationType
        });
      } catch (error) {
        console.log(error);
      }
    },
    async ON_ANSWER_RECOMMENDATION({ commit }, payload) {
      try {
        await putAction(
          `/api/recommendation-answer?objectId=${payload.answerId}`,
          {
            recommendationType: payload.recommendationType
          }
        );
        commit("UPDATE_MY_ANSWER_RECOMMENDATION", {
          answerId: payload.answerId,
          recommendationType: payload.recommendationType
        });
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    async DELETE_ANSWER_RECOMMENDATION({ commit }, answerId) {
      try {
        await deleteAction(`/api/recommendation-answer?objectId=${answerId}`);
        commit("UPDATE_MY_ANSWER_RECOMMENDATION", {
          answerId: answerId,
          recommendationType: "NOT_EXIST"
        });
      } catch (error) {
        console.log(error);
        throw error;
      }
    }
  },
  getters: {
    fetchedMyQuestionRecommendation(state) {
      return state.myQuestionRecommendation;
    },
    fetchedMyAnswerRecommendation: state => answerId => {
      return state.myAnswerRecommendation.filter(
        my => my.answerId === answerId
      )[0];
    }
  }
};
