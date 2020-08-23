import { deleteAction, getAction, postAction } from "../../api";

export default {
  state: {
    myNoticeFavorites: [],
    myQuestionFavorites: [],
    favoriteType: "NOTICE_FAVORITE"
  },

  mutations: {
    SET_NOTICE_FAVORITES(state, data) {
      state.myNoticeFavorites = data;
    },
    SET_QUESTION_FAVORITES(state, data) {
      state.myQuestionFavorites = data;
    },
    SET_FAVORITE_TYPE(state, data) {
      state.favoriteType = data;
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
    async CREATE_FAVORITE({ commit }, { queryParam, object }) {
      await postAction(`/api/favorite-${object}?` + queryParam);
      commit;
    },
    async DELETE_FAVORITE({ commit }, { objectId, object }) {
      await deleteAction(`/api/favorite-${object}?objectId=${objectId}`);
      commit;
    }
  },
  getters: {
    fetchedNoticeFavorites(state) {
      return state.myNoticeFavorites;
    },
    fetchedQuestionFavorites(state) {
      console.log(state.myQuestionFavorites);
      return state.myQuestionFavorites;
    },
    isUserNoticeFavorites: state => noticeId => {
      return state.myNoticeFavorites.some(favorite => favorite.id === noticeId);
    },
    isUserQuestionFavorites: state => questionId => {
      return state.myQuestionFavorites.some(
        favorite => favorite.id === questionId
      );
    },
    fetchedFavoriteType(state) {
      return state.favoriteType;
    }
  }
};
