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
    UPDATE_MY_ANSWER_RECOMMENDATION(state, { answerId, recommendationType }) {
      state.myAnswerRecommendation.some(answer => {
        if (answer.answerId === answerId) {
          answer.recommendationType = recommendationType;
        }
      });
    }
  },
  actions: {
    async FETCH_MY_RECOMMENDATION({ commit }, { object, objectId, userId }) {
      const { data } = await getAction(
        `/api/recommendation-${object}?objectId=${objectId}&userId=${userId}`
      );

      if (object === "question") {
        commit("SET_MY_QUESTION_RECOMMENDATION", data);
      } else {
        commit("SET_MY_ANSWER_RECOMMENDATION", {
          objectId,
          recommendationType: data.recommendationType
        });
      }
    },

    async CREATE_RECOMMENDATION(
      { commit },
      { object, objectId, recommendationType }
    ) {
      await putAction(`/api/recommendation-${object}?objectId=${objectId}`, {
        recommendationType
      });

      if (object === "question") {
        commit("SET_MY_QUESTION_RECOMMENDATION", {
          recommendationType
        });
      } else {
        commit("UPDATE_MY_ANSWER_RECOMMENDATION", {
          objectId,
          recommendationType
        });
      }
    },

    async DELETE_RECOMMENDATION({ commit }, { object, objectId }) {
      await deleteAction(`/api/recommendation-${object}?objectId=${objectId}`);

      if (object === "question") {
        commit("SET_MY_QUESTION_RECOMMENDATION", {
          recommendationType: "NOT_EXIST"
        });
      } else {
        commit("UPDATE_MY_ANSWER_RECOMMENDATION", {
          objectId,
          recommendationType: "NOT_EXIST"
        });
      }
    },

    async FETCH_MY_ANSWER_RECOMMENDATION({ commit }, { answerId, userId }) {
      const { data } = await getAction(
        `/api/recommendation-answer?objectId=${answerId}&userId=${userId}`
      );
      commit("SET_MY_ANSWER_RECOMMENDATION", {
        answerId,
        recommendationType: data.recommendationType
      });
    }
  },

  getters: {
    fetchedMyQuestionRecommendation(state) {
      return state.myQuestionRecommendation;
    },
    fetchedMyAnswerRecommendation: state => answerId => {
      return state.myAnswerRecommendation.filter(
        my => my.objectId === answerId
      )[0];
    }
  }
};
