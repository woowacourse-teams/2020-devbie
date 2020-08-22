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
    }
  },
  actions: {
    async FETCH_MY_NOTICE_FAVORITES({ commit }, userId) {
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
      console.log(state.myNoticeFavorites);
      return state.myNoticeFavorites.some(favorite => favorite.id === noticeId);
    }
  }
};
