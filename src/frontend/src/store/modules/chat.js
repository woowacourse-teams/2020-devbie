export default {
  state: {
    drawer: false,
    chats: [
      { id: 1, name: "코일", message: "테스트" },
      { id: 2, name: "코일2", message: "테스트2" },
      { id: 3, name: "코일", message: "테스트3" },
      { id: 4, name: "코일2", message: "테스트4" },
      { id: 5, name: "코일", message: "테스5" },
      { id: 6, name: "코일2", message: "테스트6" },
      { id: 7, name: "코일", message: "테스7" },
      { id: 8, name: "코일2", message: "테스트8" },
      { id: 9, name: "코일", message: "테스트9" },
      { id: 10, name: "코일2", message: "테스트10" },
      { id: 11, name: "코일", message: "테스트11" },
      { id: 12, name: "코일2", message: "테스트12" },
      { id: 13, name: "코일", message: "테스트13" },
      { id: 14, name: "코일2", message: "테스트14" }
    ]
  },
  mutations: {
    TOGGLE_DRAWER(state) {
      state.drawer = !state.drawer;
    },
    SET_CHATS(state, chats) {
      state.chats = chats;
    },
    ADD_CHAT(state, chat) {
      const newChat = {
        id: chat.id,
        name: chat.name,
        message: chat.message
      };
      state.chats.add(newChat);
    }
  },
  actions: {
    TOGGLE_DRAWER({ commit }) {
      commit("TOGGLE_DRAWER");
    }
  },
  getters: {
    drawer(state) {
      return state.drawer;
    },
    fetchedChats(state) {
      return state.chats;
    }
  }
};
