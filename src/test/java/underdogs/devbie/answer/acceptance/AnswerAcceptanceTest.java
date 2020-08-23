package underdogs.devbie.answer.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

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
    private static final long ANSWER_ID = 1L;

    @DisplayName("면접 답변 인수테스트")
    @TestFactory
    Stream<DynamicTest> answer() {
        return Stream.of(
            dynamicTest("답변 생성", () -> {
                AnswerCreateRequest request = AnswerCreateRequest.of(QUESTION_ID, ACCEPTANCE_TEST_CONTENT);
                post("/api/answers", OBJECT_MAPPER.writeValueAsString(request));
            }),
            dynamicTest("질문 id로 답변 조회", () -> {
                AnswerResponses answerResponses = get(String.format("/api/answers?questionId=%d", QUESTION_ID),
                    AnswerResponses.class);

                assertNotNull(answerResponses);
                assertThat(answerResponses.getAnswerResponses()).isNotEmpty();
                AnswerResponse answerResponse = answerResponses.getAnswerResponses().get(0);
                assertAll(
                    () -> assertEquals(answerResponse.getId(), ANSWER_ID),
                    () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID),
                    () -> assertEquals(answerResponse.getContent(), ACCEPTANCE_TEST_CONTENT)
                );
            }),
            dynamicTest("답변 수정", () -> {
                AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from(CHANGED_CONTENT);

                patch(String.format("/api/answers/%d", ANSWER_ID),
                    OBJECT_MAPPER.writeValueAsString(answerUpdateRequest));
                AnswerResponse answerResponse = get(String.format("/api/answers/%d", ANSWER_ID), AnswerResponse.class);
                assertAll(
                    () -> assertEquals(answerResponse.getId(), ANSWER_ID),
                    () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID),
                    () -> assertEquals(answerResponse.getContent(), CHANGED_CONTENT)
                );
            }),
            dynamicTest("수정한 답변 조회", () -> {
                AnswerResponse answerResponse = get(String.format("/api/answers/%d", ANSWER_ID), AnswerResponse.class);

                assertAll(
                    () -> assertEquals(answerResponse.getId(), ANSWER_ID),
                    () -> assertEquals(answerResponse.getQuestionId(), QUESTION_ID),
                    () -> assertEquals(answerResponse.getContent(), CHANGED_CONTENT)
                );
            }),
            dynamicTest("답변 삭제", () -> {
                delete(String.format("/api/answers/%d", ANSWER_ID));
            })
        );
    }
}
