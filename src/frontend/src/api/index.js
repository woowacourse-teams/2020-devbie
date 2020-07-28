import axios from "axios";

// 1. HTTP Request & Response와 관련된 기본 설정
const config = {
  baseUrl: "http://localhost:8080"
};

// 2. API 함수들을 정의
function fetchQuestionList() {
  return axios.get(`${config.baseUrl}/api/questions`);
}

function fetchQuestionDetail(questionId) {
  return axios.get(`${config.baseUrl}/api/questions/${questionId}`);
}

function fetchQuestionRecommendation(questionId) {
  return axios.get(
    `${config.baseUrl}/api/recommendation-question/${questionId}`
  );
}

function createQuestion(request) {
  const token = localStorage.getItem("devbieToken");
  return axios.post(`${config.baseUrl}/api/questions`, request, {
    headers: {
      Authorization: `bearer ${token}`
    }
  });
}

function updateQuestion(request, questionId) {
  const token = localStorage.getItem("devbieToken");
  return axios.patch(`${config.baseUrl}/api/questions/${questionId}`, request, {
    headers: {
      Authorization: `bearer ${token}`
    }
  });
}
export {
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  createQuestion,
  updateQuestion
};
