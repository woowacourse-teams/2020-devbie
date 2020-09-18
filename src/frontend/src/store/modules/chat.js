import { patchAction } from "../../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

function setSessionId(state, socket) {
  const transportUrl = socket._transport.url.split("/");
  const sessionId = transportUrl[transportUrl.length - 2];
  state.sessionId = sessionId;
}

function connectStomp(state) {
  const socket = new SockJS("/chat");
  state.stompClient = Stomp.over(socket);
  state.stompClient.debug = () => {};
  state.stompClient.connect({ notice: state.noticeId }, function() {
    setSessionId(state, socket);
    state.stompClient.subscribe("/channel/" + state.noticeId, tick => {
      const body = JSON.parse(tick.body);
      if (body.stompMethodType === "ENTER") {
        if (state.sessionId === body.data.sessionId) {
          state.name = body.data.name;
          state.color = body.data.color;
        }
        state.userCount = state.userCount + 1;
      } else if (body.stompMethodType === "QUIT") {
        state.userCount = state.userCount - 1;
      } else if (body.stompMethodType === "TALK") {
        state.chats.push(body.data);
      }
    });
  });
}

function disconnect(state) {
  state.stompClient.disconnect();
  state.stompClient = null;
}

export default {
  state: {
    stompClient: null,
    sessionId: "",
    noticeId: "",
    chatTitle: "",
    drawer: false,
    name: "",
    color: "",
    chats: [],
    userCount: "",
    chatRoomsHistory: []
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
    },
    PUT_CHAT_ROOM_HISTORY(state, { noticeId, chatRoomName }) {
      state.chatRoomsHistory.push({
        noticeId: noticeId,
        chatRoomName: chatRoomName
      });
    }
  },
  actions: {
    SEND({ commit }, data) {
      commit("SEND", data);
    },
    OPEN_LATEST({ commit, state }) {
      if (state.noticeId) {
        commit("SET_DRAWER", true);
        commit("CONNECT_LATEST");
      } else {
        this.dispatch(
          "UPDATE_SNACKBAR_TEXT",
          "최근에 들어간 채팅방이 없습니다"
        );
      }
    },
    CLOSE_DRAWER({ commit }) {
      commit("DISCONNECT");
      commit("SET_DRAWER", false);
    },
    async OPEN_DRAWER({ commit }, notice) {
      commit("SET_DRAWER", true);
      commit("CONNECT", notice);

      const { data } = await patchAction(
        "/api/chatrooms?noticeId=" + notice.id
      );
      commit("SET_CHATS", data.messageResponses.messageResponses);
      commit("SET_USER_COUNT", data.headCount);
      commit("PUT_CHAT_ROOM_HISTORY", {
        noticeId: notice.id,
        chatRoomName: notice.company.name + " - " + notice.title
      });
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
    },
    fetchedChatRoomHistory(state) {
      return state.chatRoomsHistory;
    }
  }
};
