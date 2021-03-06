import axios from "axios";

// 1. HTTP Request & Response와 관련된 기본 설정
const config = {
  baseUrl: ""
};

const devbieToken = `bearer ${localStorage.getItem("devbieToken")}`;

function getAction(url) {
  return axios.get(`${config.baseUrl}${url}`, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function postAction(url, request, configOption) {
  return axios.post(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken,
      ...configOption
    }
  });
}

function putAction(url, request) {
  return axios.put(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken
    }
  });
}

function patchAction(url, request, configOption) {
  return axios.patch(`${config.baseUrl}${url}`, request, {
    headers: {
      Authorization: devbieToken,
      ...configOption
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
