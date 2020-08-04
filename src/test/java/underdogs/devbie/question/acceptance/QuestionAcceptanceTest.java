package underdogs.devbie.question.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

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

            WHEN 질문에 해시태그를 추가한다.
            THEN 해시태그로 질문을 검색할 수 있다.

            WHEN 질문을 삭제 요청한다..
            THEN 질문이 삭제된다.
     */

    @DisplayName("면접 질문 인수테스트")
    @TestFactory
    Stream<DynamicTest> manageQuestion() {
        return Stream.of(
            dynamicTest("질문 두 개 생성", () -> {
                createQuestion(TEST_QUESTION_TITLE);
                createQuestion(TEST_TITLE_FOR_SEARCH);
            }),
            dynamicTest("전체 질문 조회", () -> {
                QuestionResponses questions = get("/api/questions", QuestionResponses.class);
                QuestionResponse firstQuestion = questions.getQuestions().get(0);
                assertAll(
                    () -> assertThat(questions.getQuestions()).hasSize(2),
                    () -> assertThat(firstQuestion.getUserId()).isEqualTo(userId),
                    () -> assertThat(firstQuestion.getTitle()).isEqualTo(TEST_QUESTION_TITLE),
                    () -> assertThat(firstQuestion.getContent()).isEqualTo(TEST_QUESTION_CONTENT)
                );
            }),
            dynamicTest("특정 질문 검색", () -> {
                QuestionResponses searchedQuestions = get("/api/questions?keyword=" + SEARCH_KEYWORD,
                    QuestionResponses.class);
                assertAll(
                    () -> assertThat(searchedQuestions.getQuestions()).hasSize(1),
                    () -> assertThat(searchedQuestions.getQuestions().get(0).getTitle()).isEqualTo(
                        TEST_TITLE_FOR_SEARCH)
                );
            }),
            dynamicTest("질문 조회", () -> {
                QuestionResponse firstQuestion = fetchFirstQuestion();
                QuestionResponse questionResponse = get("/api/questions/" + firstQuestion.getQuestionId(),
                    QuestionResponse.class);
                assertAll(
                    () -> assertThat(questionResponse.getUserId()).isEqualTo(userId),
                    () -> assertThat(questionResponse.getVisits()).isEqualTo(1L),
                    () -> assertThat(questionResponse.getTitle()).isEqualTo(TEST_QUESTION_TITLE),
                    () -> assertThat(questionResponse.getContent()).isEqualTo(TEST_QUESTION_CONTENT)
                );
            }),
            dynamicTest("질문 수정", () -> {
                QuestionResponse firstQuestion = fetchFirstQuestion();
                QuestionUpdateRequest updateRequest = QuestionUpdateRequest.builder()
                    .title("Changed Title")
                    .content("Changed Content")
                    .build();
                String inputJsonForUpdate = objectMapper.writeValueAsString(updateRequest);
                patch("/api/questions/" + firstQuestion.getQuestionId(), inputJsonForUpdate);
                QuestionResponse updatedQuestion = get("/api/questions/" + firstQuestion.getQuestionId(),
                    QuestionResponse.class);
                assertAll(
                    () -> assertThat(updatedQuestion.getUserId()).isEqualTo(userId),
                    () -> assertThat(updatedQuestion.getVisits()).isEqualTo(2L),
                    () -> assertThat(updatedQuestion.getTitle()).isEqualTo("Changed Title"),
                    () -> assertThat(updatedQuestion.getContent()).isEqualTo("Changed Content")
                );
            }),
            dynamicTest("질문 삭제", () -> {
                QuestionResponse firstQuestion = fetchFirstQuestion();
                delete("/api/questions/" + firstQuestion.getQuestionId());
                QuestionResponses questions = get("/api/questions", QuestionResponses.class);
                assertThat(questions.getQuestions()).hasSize(1);
            })
        );

    }

    private void createQuestion(String title) throws JsonProcessingException {
        QuestionCreateRequest createRequest = QuestionCreateRequest.builder()
            .title(title)
            .content(TEST_QUESTION_CONTENT)
            .build();
        String inputJsonForCreate = objectMapper.writeValueAsString(createRequest);
        post("/api/questions", inputJsonForCreate);
    }

    private QuestionResponse fetchFirstQuestion() {
        QuestionResponses questions = get("/api/questions", QuestionResponses.class);
        return questions.getQuestions().get(0);
    }
}
