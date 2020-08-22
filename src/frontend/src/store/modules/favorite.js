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
    async FETCH_MY_NOTICE_FAVORITES({ commit }, userId) {
      console.log(1111111111);
      const { data } = await getAction(`/api/favorite-notice?userId=${userId}`);
      commit("SET_NOTICE_FAVORITES", data["noticeResponses"]);
    },
    async CREATE_FAVORITE({ commit }, queryUrl) {
      await postAction(`/api/favorite-notice?` + queryUrl);
      commit;
    },
    async DELETE_FAVORITE({ commit }, objectId) {
      await deleteAction(`/api/favorite-notice?objectId=${objectId}`);
      commit;
    }
  },
  getters: {
    fetchedNoticeFavorites(state) {
      return state.myNoticeFavorites;
    },
    isUserNoticeFavorites: state => noticeId => {
      return state.myNoticeFavorites.some(favorite => favorite.id === noticeId);
    },
    fetchedFavoriteType(state) {
      return state.favoriteType;
    }
  }
};
