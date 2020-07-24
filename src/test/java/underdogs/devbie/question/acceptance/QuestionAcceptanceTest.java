package underdogs.devbie.question.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.acceptance.AcceptanceTest;

public class QuestionAcceptanceTest extends AcceptanceTest {

    public static final String TEST_QUESTION_TITLE = "test_question_title";
    public static final String TEST_QUESTION_CONTENT = "test_question_content";

    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
