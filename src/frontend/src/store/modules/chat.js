import { patchAction, deleteAction } from "../../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

function connectStomp(state) {
  const socket = new SockJS("/chat");
  state.stompClient = Stomp.over(socket);
  state.stompClient.connect({}, function(frame) {
    console.log("frame: " + frame);
    state.stompClient.subscribe("/channel/" + state.noticeId, tick => {
      const data = JSON.parse(tick.body);
      if (data.stompMethodType === "ENTER") {
        state.userCount = state.userCount + 1;
      } else if (data.stompMethodType === "QUIT") {
        state.userCount = state.userCount - 1;
      } else if (data.stompMethodType === "TALK") {
        state.chats.push(data.data);
      }
    });
  });
}

function disconnect(state) {
  deleteAction("/api/chatrooms/" + state.name + "?noticeId=" + state.noticeId);
  state.stompClient.disconnect();
}

export default {
  state: {
    stompClient: null,
    noticeId: "",
    chatTitle: "",
    drawer: false,
    name: "",
    color: "",
    chats: [],
    userCount: ""
  },
  mutations: {
    CONNECT(state, notice) {
      if (state.stompClient) {
        disconnect(state);
      }
      state.noticeId = notice.id;
      state.chatTitle = notice.company.name + " - " + notice.title;
      connectStomp(state);
    },
    CONNECT_LATEST(state) {
      if (state.noticeId) {
        connectStomp(state);
      }
    },
    DISCONNECT(state) {
      disconnect(state);
    },
    SEND(state, { name, message }) {
      if (message.length === 1) {
        return;
      }
      const request = {
        noticeId: state.noticeId,
        name: name,
        titleColor: state.color,
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
    },
    SET_COLOR(state, color) {
      state.color = color;
    },
    SET_USER_COUNT(state, userCount) {
      state.userCount = userCount;
    }
  },
  actions: {
    SEND({ commit }, data) {
      commit("SEND", data);
    },
    async OPEN_DRAWER({ commit }, notice) {
      commit("SET_DRAWER", true);
      commit("CONNECT", notice);

      const { data } = await patchAction(
        "/api/chatrooms?noticeId=" + notice.id
      );
      commit("SET_NAME", data.nickName);
      commit("SET_CHATS", data.messageResponses.messageResponses);
      commit("SET_COLOR", data.titleColor);
      commit("SET_USER_COUNT", data.headCount);
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
    },
    fetchedUserCount(state) {
      return state.userCount;
    }
  }
};
