import { getAction } from "../../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  state: {
    stompClient: {},
    noticeId: "",
    drawer: false,
    chats: [{ id: 0, name: "유안", message: "임시" }]
  },
  mutations: {
    CONNECT(state, noticeId) {
      state.noticeId = noticeId;
      const socket = new SockJS("/chat");
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function(frame) {
        console.log("frame: " + frame);
        state.stompClient.subscribe("/channel/" + noticeId, tick => {
          state.chats.push(JSON.parse(tick.body));
        });
      });
    },
    DISCONNECT(state) {
      state.stompClient.disconnect();
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
      commit("DISCONNECT");
      commit("SET_DRAWER", false);
    },
    async FETCH_CHATS({ commit }, noticeId) {
      const { data } = await getAction("/api/chats?noticeId=" + noticeId);
      commit("SET_CHATS", data.messageResponses.messageResponses);
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
