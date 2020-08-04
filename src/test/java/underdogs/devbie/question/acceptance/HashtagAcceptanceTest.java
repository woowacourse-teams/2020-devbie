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
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;

public class HashtagAcceptanceTest extends AcceptanceTest {

    /*
    Feature: 해시태그 관리

    Scenario: 해시태그를 관리한다.
            WHEN 해시태그를 생성을 요청한다.
            THEN 해시태그가 생성된다.

            WHEN 해시태그 목록을 조회를 요청한다.
            THEN 생성된 해시태그 목록을 불러온다.

            WHEN 해시태그를 Id로 단건 조회한다.
            THEN Id값으로 해시태그를 불러온다.

            WHEN 해시태그를 이름으로 조회한다.
            THEN 이름에 해당하는 해시태그를 불러온다.

            WHEN 해시태그 내용 수정을 요청한다.
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
            }),
            dynamicTest("해시태그 목록 조회", () -> {
                HashtagResponses hashtagResponses = get("/api/hashtags", HashtagResponses.class);

                assertAll(
                    () -> assertThat(hashtagResponses.getHashtags().get(0).getTagName()).isEqualTo("java"),
                    () -> assertThat(hashtagResponses.getHashtags().get(1).getTagName()).isEqualTo("network")
                );
            }),
            dynamicTest("해시태그 단건 조회", () -> {
                HashtagResponses hashtagResponses = get("/api/hashtags", HashtagResponses.class);
                Long hashtagId = hashtagResponses.getHashtags().get(0).getId();
                HashtagResponse hashtagResponse = get("/api/hashtags/" + hashtagId, HashtagResponse.class);

                assertAll(
                    () -> assertThat(hashtagResponse.getId()).isEqualTo(hashtagId),
                    () -> assertThat(hashtagResponse.getTagName()).isEqualTo("java")
                );
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
