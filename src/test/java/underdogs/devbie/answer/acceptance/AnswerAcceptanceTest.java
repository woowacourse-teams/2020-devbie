package underdogs.devbie.answer.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;

public class AnswerAcceptanceTest extends AcceptanceTest {

    /*
    Feature: 면접 답변 관리

     Scenario: 면접 답변을 관리한다.

         When 질문에 해당하는 답변 1개를 추가 요청한다.
         Then 답변이 업로드 되었다.

         When 답변 전체를 조회 요청한다.
         Then 답변 전체가 조회된다.

         When 질문에 해당하는 답변 전체를 조회 요청한다.
         Then 질문에 해당하는 답변 전체가 조회된다.

         When 답변 내용을 수정한다.
         Then 답변이 수정 되었다.

         When 질문에 해당하는 답변 1개를 조회 요청한다.
         Then 답변 1개가 조회된다.

         When 답변을 삭제한다.
         Then 답변이 삭제되었다.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String CHANGED_CONTENT = "Changed Content";
    private static final long QUESTION_ID = 1L;
    private static final String ACCEPTANCE_TEST_CONTENT = "ACCEPTANCE_TEST_CONTENT";

    @DisplayName("면접 답변 인수테스트")
    @Test
    void answer() throws JsonProcessingException {
        createAnswer();
        AnswerResponses answerResponses = readAllAnswer();
        readAnswerByQuestionId();
        AnswerResponse answerResponse = answerResponses.getAnswerResponses().get(0);
        // updateAnswer(answerResponse.getId());
        readAnswer(answerResponse.getId());
        // deleteAnswer(answerResponse.getId());
    }

    private void createAnswer() throws JsonProcessingException {
        AnswerCreateRequest request = AnswerCreateRequest.of(QUESTION_ID, ACCEPTANCE_TEST_CONTENT);
        post("/api/answers", OBJECT_MAPPER.writeValueAsString(request));
    }

    private AnswerResponses readAllAnswer() {
        AnswerResponses answerResponses = get("/api/answers", AnswerResponses.class);

        assertNotNull(answerResponses);
        assertThat(answerResponses.getAnswerResponses()).isNotEmpty();
        AnswerResponse answerResponse = answerResponses.getAnswerResponses().get(0);
        assertAll(
            () -> assertEquals(answerResponse.getId(), 1L),
            () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID)
            // () -> assertEquals(answerResponse.getContent(), ACCEPTANCE_TEST_CONTENT)
        );
        return answerResponses;
    }

    private void readAnswerByQuestionId() {
        AnswerResponses answerResponses = get(String.format("/api/answers?questionId=%d", QUESTION_ID),
            AnswerResponses.class);

        assertNotNull(answerResponses);
        assertThat(answerResponses.getAnswerResponses()).isNotEmpty();
        AnswerResponse answerResponse = answerResponses.getAnswerResponses().get(0);
        assertAll(
            () -> assertEquals(answerResponse.getId(), 1L),
            () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID)
            // () -> assertEquals(answerResponse.getContent(), ACCEPTANCE_TEST_CONTENT)
        );
    }

    private void updateAnswer(Long answerId) throws JsonProcessingException {
        AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from(CHANGED_CONTENT);

        patch(String.format("/api/answers/%d", answerId), OBJECT_MAPPER.writeValueAsString(answerUpdateRequest));
        AnswerResponse answerResponse = get(String.format("/api/answers/%d", answerId), AnswerResponse.class);
        assertAll(
            () -> assertEquals(answerResponse.getId(), answerId),
            () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID)
            // () -> assertEquals(answerResponse.getContent(), CHANGED_CONTENT)
        );
    }

    private void readAnswer(Long answerId) {
        AnswerResponse answerResponse = get(String.format("/api/answers/%d", answerId), AnswerResponse.class);

        assertAll(
            () -> assertEquals(answerResponse.getId(), 1L),
            () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID)
            // () -> assertEquals(answerResponse.getContent(), CHANGED_CONTENT)
        );
    }

    private void deleteAnswer(Long answerId) {
        delete(String.format("/api/answers/%d", answerId));

        AnswerResponses answers = get("/api/answers", AnswerResponses.class);
        // assertThat(answers.getAnswerResponses()).hasSize(0);
    }
}
