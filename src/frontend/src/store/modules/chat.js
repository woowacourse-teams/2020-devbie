import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  state: {
    stompClient: {},
    noticeId: "",
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
      { id: 10, name: "코일2", message: "테스트10" }
    ]
  },
  mutations: {
    CONNECT(state, noticeId) {
      console.log("연결되나?");
      state.noticeId = noticeId;
      // console.log("noticeId: " + noticeId);
      // console.log("sockJs: " + JSON.stringify(new SockJS("/chat")));
      const socket = new SockJS("/chat");
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function(frame) {
        console.log("frame: " + frame);
        state.stompClient.subscribe("/channel/" + noticeId, tick => {
          state.chats.push(JSON.parse(tick.body));
          // state.chats.add(JSON.parse(tick.body));
        });
      });
    },
    SEND(state, { name, message }) {
      const request = {
        noticeId: state.noticeId,
        name: name,
        message: message
      };
      state.stompClient.send("/send/message", JSON.stringify(request), {});
    },
    SET_DRAWER(state, value) {
      state.drawer = value;
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
    CONNECT({ commit }, noticeId) {
      commit("CONNECT", noticeId);
    },
    SEND({ commit }, data) {
      commit("SEND", data);
    },
    OPEN_DRAWER({ commit }) {
      commit("SET_DRAWER", true);
    },
    CLOSE_DRAWER({ commit }) {
      commit("SET_DRAWER", false);
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
