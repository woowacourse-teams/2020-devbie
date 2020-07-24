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
         When 질문에 해당하는 답변 전체를 조회 요청한다.
         Then 답변 전체가 조회된다.
         When 답변 내용을 수정한다.
         Then 답변이 수정 되었다.
         When 질문에 해당하는 답변 1개를 조회 요청한다.
         Then 답변 1개가 조회된다.
         When 답변을 삭제한다.
         Then 답변이 삭제되었다.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private AnswerCreateRequest answerCreateRequest;
    private AnswerResponses answerResponses;
    private AnswerUpdateRequest answerUpdateRequest;

    @DisplayName("면접 답변 인수테스트")
    @Test
    void answer() throws JsonProcessingException {
        answerCreateRequest = new AnswerCreateRequest(1L, "test content");

        createAnswer();
        readAllAnswer();
        updateAnswer();
    }

    private void createAnswer() throws JsonProcessingException {
        post("/api/answers", OBJECT_MAPPER.writeValueAsString(answerCreateRequest));
    }

    private void readAllAnswer() {
        answerResponses = get("/api/answers", AnswerResponses.class);

        assertNotNull(answerResponses);
        assertThat(answerResponses.getAnswerResponses()).isNotEmpty();
        AnswerResponse answerResponse = answerResponses.getAnswerResponses().get(0);
        assertAll(
            () -> assertEquals(answerResponse.getId(), 1L),
            () -> assertEquals(answerResponse.getQuestionId(), answerCreateRequest.getQuestionId()),
            () -> assertEquals(answerResponse.getContent(), answerCreateRequest.getContent())
        );
    }

    private void updateAnswer() throws JsonProcessingException {
        Long id = answerResponses.getAnswerResponses().get(0).getId();
        answerUpdateRequest = new AnswerUpdateRequest("Changed Content");

        patch(String.format("/api/answers/%d", id), OBJECT_MAPPER.writeValueAsString(answerUpdateRequest));
    }
}
