package underdogs.devbie.question.acceptance;

import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.question.dto.HashtagCreateRequest;

public class HashtagAcceptanceTest extends AcceptanceTest {

    /*
    Feature: 해시태그 관리

    Scenario: 해시태그를 관리한다.
            WHEN 해시태그를 생성을 요청한다.
            THEN 해시태그가 생성된다.

            WHEN 해시태그 목록을 조회를 요청한다.
            THEN 생성된 해시태그 목록을 불러온다.

            WHEN 해시태그 내용을 수정을 요청한다.
            THEN 해시태그 내용이 수정된다.

            WHEN 해시태그를 삭제 요청을 보낸다.
            THEN 해시태그가 삭제된다.
    */

    @DisplayName("해시태그 인수테스트")
    @TestFactory
    Stream<DynamicTest> manageHashtags() {
        return Stream.of(
            dynamicTest("해시태그 생성", () -> {
                createHashtag("java");
                createHashtag("network");
            })
        );
    }

    private void createHashtag(String tagName) throws JsonProcessingException {
        HashtagCreateRequest hashtagCreateRequest = HashtagCreateRequest.builder()
            .tagName(tagName)
            .build();
        String inputJson = objectMapper.writeValueAsString(hashtagCreateRequest);
        post("/api/hashtags", inputJson);
    }
}
