import { getAction } from "../../api";

export default {
  state: {
    hashtags: []
  },
  mutations: {
    SET_HASHTAGS(state, data) {
      state.hashtags = data;
    }
  },
  actions: {
    async FETCH_HASHTAGS({ commit }) {
      try {
        const { data } = await getAction(`/api/hashtags`);
        commit("SET_HASHTAGS", data);
      } catch (error) {
        console.log(error);
      }
    }
  },
  getters: {
    fetchedHashtags(state) {
      return state.hashtags;
    }
  }
};
