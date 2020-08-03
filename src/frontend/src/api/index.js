import axios from "axios";

// 1. HTTP Request & Response와 관련된 기본 설정
const config = {
  baseUrl: "http://localhost:8080"
};

const devbieToken = `bearer ${localStorage.getItem("devbieToken")}`;

function getAction(url) {
  return axios.get(`${config.baseUrl}${url}`, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function postAction(url, request) {
  return axios.post(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function putAction(url, request) {
  return axios.post(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function patchAction(url, request) {
  return axios.patch(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function deleteAction(url) {
  return axios.delete(`${config.baseUrl}${url}`, {
    headers: {
      Authorization: devbieToken
    }
  });
}

export { getAction, postAction, putAction, patchAction, deleteAction };
