export default {
  state: {
    snackbarState: false,
    snackbarText: ""
  },
  mutations: {
    SET_SNACKBAR_STATE(state, snackbarState) {
      state.snackbarState = snackbarState;
    },
    SET_SNACKBAR_TEXT(state, snackbarText) {
      state.snackbarText = snackbarText;
    }
  },
  actions: {
    UPDATE_SNACKBAR_STATE({ commit }, snackbarState) {
      commit("SET_SNACKBAR_STATE", snackbarState);
    },
    UPDATE_SNACKBAR_TEXT({ commit }, snackbarText) {
      commit("SET_SNACKBAR_STATE", true);
      commit("SET_SNACKBAR_TEXT", snackbarText);
    }
  },
  getters: {
    fetchedSnackBarState(state) {
      return state.snackbarState;
    },
    fetchedSnackBarText(state) {
      return state.snackbarText;
    }
  }
};
