import { deleteAction, getAction, patchAction, postAction } from "../../api";

export default {
  state: {
    answers: []
  },
  mutations: {
    SET_ANSWERS(state, data) {
      state.answers = data;
    },
    UPDATE_ANSWER(state, { answerId, updateContent }) {
      state.answers.some(answer => {
        if (answer.id === answerId) {
          answer.content = updateContent;
          return true;
        }
        return false;
      });
    },
    DELETE_ANSWER(state, id) {
      state.answers = state.answers.filter(answer => answer.id !== id);
    },
    CREATE_ANSWER(state, payload) {
      state.answers.push(payload);
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
      const updateContent = payload.updateContent;
      const response = await patchAction(`/api/answers/${payload.answerId}`, {
        content: updateContent
      });
      commit("UPDATE_ANSWER", payload);
      return response;
    },
    async DELETE_ANSWER({ commit }, answerId) {
      const response = await deleteAction(`/api/answers/${answerId}`);
      commit("DELETE_ANSWER", answerId);
      return response;
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
