import { deleteAction, getAction } from "../../api";

export default {
  state: {
    answers: []
  },
  mutations: {
    SET_ANSWERS(state, data) {
      state.answers = data;
    },
    UPDATE_ANSWER(state, id, content) {
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
    async DELETE_ANSWER({ commit }, answerId) {
      try {
        await deleteAction(`/api/answers/${answerId}`).then(() => {
          commit("DELETE_ANSWER", answerId);
        });
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedAnswers(state) {
      return state.answers;
    }
  }
};
