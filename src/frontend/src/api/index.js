import axios from "axios";

// 1. HTTP Request & Response와 관련된 기본 설정
const config = {
  baseUrl: "http://localhost:8080"
};

// 2. API 함수들을 정의
function fetchLoginUser() {
  const token = localStorage.getItem("devbieToken");
  return axios.get("/api/users", {
    headers: {
      Authorization: `bearer ${token}`
    }
  });
}

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

function fetchNotices() {
  return axios.get(`${config.baseUrl}/api/notices`);
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

function deleteQuestion(questionId) {
  const token = localStorage.getItem("devbieToken");
  return axios.delete(`${config.baseUrl}/api/questions/${questionId}`, {
    headers: {
      Authorization: `bearer ${token}`
    }
  });
}

function fetchAnswers(questionId) {
  return axios.get(`${config.baseUrl}/api/answers?questionId=${questionId}`);
}

function updateAnswer(answerId, content) {
  axios.patch(
    `${config.baseUrl}/api/answers/${answerId}`,
    {
      content: content
    },
    {
      headers: {
        Authorization: "bearer " + localStorage.getItem("devbieToken")
      }
    }
  );
}

function deleteAnswer(answerId) {
  axios.delete(`${config.baseUrl}/api/answers/${answerId}`, {
    headers: {
      Authorization: "bearer " + localStorage.getItem("devbieToken")
    }
  });
}

export {
  fetchLoginUser,
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  fetchAnswers,
  updateAnswer,
  deleteAnswer,
  createQuestion,
  updateQuestion,
  deleteQuestion,
  fetchNotices
};
