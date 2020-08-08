import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    answers: []
  },
  mutations: {
    SET_ANSWERS(state, data) {
      state.answers = data;
    },
    UPDATE_ANSWER(state, payload) {
      state.answers.some(answer => {
        if (answer.id === payload.answerId) {
          answer.content = payload.updateContent;
          return true;
        }
        return false;
      });
    },
    DELETE_ANSWER(state, id) {
      state.answers = state.answers.filter(answer => answer.id !== id);
    },
    CREATE_ANSWER(state, payload) {
      state.answers.push({
        id: payload.id,
        userId: payload.userId,
        questionId: payload.questionId,
        content: payload.content
      });
    }
  },
  actions: {
    async FETCH_ANSWERS({ commit }, questionId) {
      try {
        const { data } = await getAction(
          `/api/answers?questionId=${questionId}`
        );
        commit("SET_ANSWERS", data.answerResponses);
      } catch (error) {
        console.log(error);
      }
    },
    async UPDATE_ANSWER({ commit }, payload) {
      try {
        const updateContent = payload.updateContent;
        await patchAction(`/api/answers/${payload.answerId}`, {
          content: updateContent
        });
        commit("UPDATE_ANSWER", payload);
      } catch (error) {
        console.log(error);
      }
    },
    async DELETE_ANSWER({ commit }, answerId) {
      try {
        await deleteAction(`/api/answers/${answerId}`).then(() => {
          commit("DELETE_ANSWER", answerId);
        });
      } catch (error) {
        console.log(error);
      }
    },
    async CREATE_ANSWER({ commit }, payload) {
      const questionId = payload.questionId;
      const content = payload.content;
      const createdResponse = await postAction(`/api/answers`, {
        questionId,
        content
      });
      const createdAnswerLocation = createdResponse.headers.location;
      const createdItemResponse = await getAction(createdAnswerLocation);
      commit("CREATE_ANSWER", createdItemResponse.data);
      return createdResponse;
    }
  },
  getters: {
    fetchedAnswers(state) {
      return state.answers;
    }
  }
};
