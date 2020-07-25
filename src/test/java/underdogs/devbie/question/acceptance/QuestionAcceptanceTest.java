package underdogs.devbie.question.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;

public class QuestionAcceptanceTest extends AcceptanceTest {

    public static final String TEST_QUESTION_TITLE = "test_question_title";
    public static final String TEST_QUESTION_CONTENT = "test_question_content";
    public static final String TEST_TITLE_FOR_SEARCH = "테스트용 검색 타이틀";
    public static final String SEARCH_KEYWORD = "검색";

    /*
    Feature: 질문 관리

    Scenario: 질문을 관리한다.
            WHEN 회원 가입을 한다.
            THEN 유저를 반환한다.

            WHEN 질문 생성 요청을 한다.
            THEN 질문이 생성 된다.

            WHEN 질문 목록 조회를 요청한다.
            THEN 생성된 질문 목록을 불러온다.

            WHEN 질문 상세 조회를 요청을 한다.
            THEN 질문 상세 정보를 확인한다.

            WHEN 질문의 제목과 내용을 수정을 요청한다.
            THEN 질문 제목과 내용이 수정된다.

            WHEN 질문의 제목에 포함된 단어로 검색한다.
            THEN 해당 단어가 포함된 질문 목록이 조회된다.

            WHEN 질문을 삭제 요청한다..
            THEN 질문이 삭제된다.
     */

    @DisplayName("면접 질문 인수테스트")
    @Test
    void manageQuestion() throws JsonProcessingException {
        createQuestion(TEST_QUESTION_TITLE);
        createQuestion(TEST_TITLE_FOR_SEARCH);
        QuestionResponses questions = readAllQuestions();

        QuestionResponse firstQuestion = questions.getQuestions().get(0);
        searchQuestion(SEARCH_KEYWORD);
        readQuestion(firstQuestion);
        updateQuestion(firstQuestion);
        deleteQuestion(firstQuestion);
    }

    private void createQuestion(String title) throws JsonProcessingException {
        QuestionCreateRequest createRequest = QuestionCreateRequest.builder()
            .title(title)
            .content(TEST_QUESTION_CONTENT)
            .build();
        String inputJsonForCreate = objectMapper.writeValueAsString(createRequest);
        post("/api/questions", inputJsonForCreate, bearerToken);
    }

    private QuestionResponses readAllQuestions() {
        QuestionResponses questions = get("/api/questions", QuestionResponses.class);
        QuestionResponse firstQuestion = questions.getQuestions().get(0);
        assertAll(
            () -> assertThat(questions.getQuestions()).hasSize(2),
            () -> assertThat(firstQuestion.getUserId()).isEqualTo(1L),
            () -> assertThat(firstQuestion.getTitle()).isEqualTo(TEST_QUESTION_TITLE),
            () -> assertThat(firstQuestion.getContent()).isEqualTo(TEST_QUESTION_CONTENT)
        );
        return questions;
    }

    private void searchQuestion(String keyword) {
        QuestionResponses searchedQuestions = get("/api/questions?keyword=" + keyword, QuestionResponses.class);
        assertAll(
            () -> assertThat(searchedQuestions.getQuestions()).hasSize(1),
            () -> assertThat(searchedQuestions.getQuestions().get(0).getTitle()).isEqualTo(TEST_TITLE_FOR_SEARCH)
        );
    }

    private void readQuestion(QuestionResponse firstQuestion) {
        QuestionResponse questionResponse = get("/api/questions/" + firstQuestion.getQuestionId(),
            QuestionResponse.class);
        assertAll(
            () -> assertThat(questionResponse.getUserId()).isEqualTo(1L),
            () -> assertThat(questionResponse.getVisits()).isEqualTo(0L),
            () -> assertThat(questionResponse.getTitle()).isEqualTo(TEST_QUESTION_TITLE),
            () -> assertThat(questionResponse.getContent()).isEqualTo(TEST_QUESTION_CONTENT)
        );
    }

    private void updateQuestion(QuestionResponse firstQuestion) throws JsonProcessingException {
        QuestionUpdateRequest updateRequest = QuestionUpdateRequest.builder()
            .title("Changed Title")
            .content("Changed Content")
            .build();
        String inputJsonForUpdate = objectMapper.writeValueAsString(updateRequest);
        patch("/api/questions/" + firstQuestion.getQuestionId(), inputJsonForUpdate, bearerToken);
        QuestionResponse updatedQuestion = get("/api/questions/" + firstQuestion.getQuestionId(),
            QuestionResponse.class);
        assertAll(
            () -> assertThat(updatedQuestion.getUserId()).isEqualTo(1L),
            () -> assertThat(updatedQuestion.getVisits()).isEqualTo(0L),
            () -> assertThat(updatedQuestion.getTitle()).isEqualTo("Changed Title"),
            () -> assertThat(updatedQuestion.getContent()).isEqualTo("Changed Content")
        );
    }

    private void deleteQuestion(QuestionResponse firstQuestion) {
        delete("/api/questions/" + firstQuestion.getQuestionId(), bearerToken);
        QuestionResponses questions = get("/api/questions", QuestionResponses.class);
        assertThat(questions.getQuestions()).hasSize(1);
    }
}
