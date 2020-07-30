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
  fetchQuestionList,
  fetchQuestionDetail,
  fetchQuestionRecommendation,
  fetchAnswers,
  updateAnswer,
  deleteAnswer
};
