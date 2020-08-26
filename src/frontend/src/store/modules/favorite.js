import { deleteAction, getAction, postAction } from "../../api";

export default {
  state: {
    myNoticeFavorites: [],
    myQuestionFavorites: []
  },

  mutations: {
    SET_NOTICE_FAVORITES(state, data) {
      state.myNoticeFavorites = data;
    },
    SET_QUESTION_FAVORITES(state, data) {
      state.myQuestionFavorites = data;
    },
    DELETE_NOTICE_FAVORITES(state) {
      state.myNoticeFavorites = [];
    },
    DELETE_QUESTION_FAVORITES(state) {
      state.myQuestionFavorites = [];
    }
  },
  actions: {
    async FETCH_MY_FAVORITES({ commit }, { object, userId }) {
      const { data } = await getAction(
        `/api/favorite-${object}?userId=${userId}`
      );
      if (object === "notice") {
        commit("SET_NOTICE_FAVORITES", data["noticeResponses"]);
      } else {
        commit("SET_QUESTION_FAVORITES", data["questions"]);
      }
    },

    // eslint-disable-next-line no-unused-vars
    async CREATE_FAVORITE({ commit }, { queryParam, object }) {
      await postAction(`/api/favorite-${object}?` + queryParam);
    },
    // eslint-disable-next-line no-unused-vars
    async DELETE_FAVORITE({ commit }, { objectId, object }) {
      await deleteAction(`/api/favorite-${object}?objectId=${objectId}`);
    }
  },
  getters: {
    fetchedNoticeFavorites(state) {
      return state.myNoticeFavorites;
    },
    fetchedQuestionFavorites(state) {
      return state.myQuestionFavorites;
    },
    isUserNoticeFavorites: state => noticeId => {
      return state.myNoticeFavorites.some(favorite => favorite.id === noticeId);
    },
    isUserQuestionFavorites: state => questionId => {
      return state.myQuestionFavorites.some(
        favorite => favorite.id === questionId
      );
    }
  }
};
