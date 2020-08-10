import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    questions: [],
    question: [],
    questionId: []
  },
  mutations: {
    SET_QUESTIONS(state, data) {
      state.questions = data;
    },
    SET_QUESTION(state, data) {
      state.question = data;
    },
    SET_NEW_QUESTION_ID(state, data) {
      state.questionId = data;
    },
    CLEAR_HASHTAGS(state) {
      state.question.hashtags = [];
    },
    SET_RECOMMENDATION_COUNT(state, data) {
      if (data.priorType === data.newType) {
        if (data.newType === "RECOMMENDED") {
          state.question.recommendedCount -= 1;
          return;
        }
        state.question.nonRecommendedCount -= 1;
      }

      if (data.newType === "RECOMMENDED") {
        if (data.priorType === "NON_RECOMMENDED") {
          state.question.nonRecommendedCount -= 1;
        }
        state.question.recommendedCount += 1;
      }

      if (data.newType === "NON_RECOMMENDED") {
        if (data.priorType === "RECOMMENDED") {
          state.question.recommendedCount -= 1;
        }
        state.question.nonRecommendedCount += 1;
      }
    }
  },
  actions: {
    async FETCH_QUESTIONS({ commit }, orderBy) {
      try {
        const { data } = await getAction(`/api/questions?orderBy=${orderBy}`);
        commit("SET_QUESTIONS", data);
      } catch (error) {
        console.log(error);
      }
    },
    async FETCH_QUESTION({ commit }, questionId) {
      try {
        const { data } = await getAction(`/api/questions/${questionId}`);
        commit("SET_QUESTION", data);
      } catch (error) {
        console.log(error);
      }
    },
    async CREATE_QUESTION({ commit }, request) {
      try {
        const response = await postAction("/api/questions", request);
        const id = response["headers"].location.split("/")[3];
        commit("SET_NEW_QUESTION_ID", id);
      } catch (error) {
        console.log(error);
      }
    },
    async UPDATE_QUESTION({ commit }, payload) {
      try {
        await patchAction(`/api/questions/${payload.questionId}`, {
          title: payload.title,
          content: payload.content,
          hashtags: payload.hashtags
        });
        commit();
      } catch (error) {
        console.log(error);
      }
    },
    async DELETE_QUESTION({ commit }, questionId) {
      try {
        await deleteAction(`/api/questions/${questionId}`);
        commit();
      } catch (error) {
        console.log(error);
      }
    },
    async FETCH_QUESTIONS_BY_HASHTAG({ commit }, hashtag) {
      try {
        const { data } = await getAction(`/api/questions?hashtag=${hashtag}`);
        commit("SET_QUESTIONS", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedQuestions(state) {
      return state.questions;
    },
    fetchedQuestion(state) {
      return state.question;
    },
    fetchedNewCreatedQuestionId(state) {
      return state.questionId;
    }
  }
};
