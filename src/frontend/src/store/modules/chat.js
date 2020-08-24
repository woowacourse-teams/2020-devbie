import { getAction } from "../../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  state: {
    stompClient: null,
    noticeId: "",
    chatTitle: "",
    drawer: false,
    name: "",
    chats: []
  },
  mutations: {
    CONNECT(state, notice) {
      if (state.stompClient) {
        state.stompClient.disconnect();
      }
      state.noticeId = notice.id;
      state.chatTitle = notice.company.name + " - " + notice.title;
      const socket = new SockJS("/chat");
      state.stompClient = Stomp.over(socket);
      state.stompClient.connect({}, function(frame) {
        console.log("frame: " + frame);
        state.stompClient.subscribe("/channel/" + state.noticeId, tick => {
          state.chats.push(JSON.parse(tick.body));
        });
      });
    },
    CONNECT_LATEST(state) {
      if (state.noticeId) {
        const socket = new SockJS("/chat");
        state.stompClient = Stomp.over(socket);
        state.stompClient.connect({}, function(frame) {
          console.log("frame: " + frame);
          state.stompClient.subscribe("/channel/" + state.noticeId, tick => {
            state.chats.push(JSON.parse(tick.body));
          });
        });
      }
    },
    DISCONNECT(state) {
      state.stompClient.disconnect();
    },
    SEND(state, { name, message }) {
      if (message.length === 1) {
        return;
      }
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
    SET_NAME(state, name) {
      state.name = name;
    },
    SET_CHATS(state, chats) {
      state.chats = chats;
    }
  },
  actions: {
    SEND({ commit }, data) {
      commit("SEND", data);
    },
    async OPEN_DRAWER({ commit }, notice) {
      commit("SET_DRAWER", true);
      commit("CONNECT", notice);

      const { data } = await getAction("/api/chats?noticeId=" + notice.id);
      commit("SET_NAME", data.nickName);
      commit("SET_CHATS", data.messageResponses.messageResponses);
    },
    OPEN_LATEST({ commit, state }) {
      if (state.noticeId) {
        commit("SET_DRAWER", true);
        commit("CONNECT_LATEST");
      } else {
        alert("최근에 들어간 채팅방이 없습니다");
      }
    },
    CLOSE_DRAWER({ commit }) {
      commit("DISCONNECT");
      commit("SET_DRAWER", false);
    }
  },
  getters: {
    drawer(state) {
      return state.drawer;
    },
    fetchedNickName(state) {
      return state.name;
    },
    fetchedChats(state) {
      return state.chats;
    },
    fetchedChatTitle(state) {
      return state.chatTitle;
    }
  }
};
