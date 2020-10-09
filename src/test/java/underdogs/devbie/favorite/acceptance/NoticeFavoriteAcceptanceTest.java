package underdogs.devbie.favorite.acceptance;

import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import underdogs.devbie.acceptance.AcceptanceTest;
import underdogs.devbie.notice.dto.NoticeResponse;
import underdogs.devbie.notice.dto.NoticeResponses;

public class NoticeFavoriteAcceptanceTest extends AcceptanceTest {

    public static final String NOTICE_FAVORITE_URI = "/api/favorite-underdogs.devbie.notice?objectType=underdogs.devbie.notice";

    @DisplayName("즐겨찾기 인수 테스트")
    @TestFactory
    Stream<DynamicTest> manageFavorite() {
        return Stream.of(
            dynamicTest("공고 생성", () -> {
                createNotice("언더독스 채용");
                createNotice("보스독스 채용");
                createNotice("독스독스 채용");
            }),
            dynamicTest("1번 공고 즐겨찾기 추가", () -> {
                addFavorite(1L);
            }),
            dynamicTest("1번 공고 즐겨찾기 확인", () -> {
                NoticeResponses noticeResponses = searchFavorite(userId);
                assertThat(noticeResponses.getNoticeResponses().get(0))
                    .extracting(NoticeResponse::getId)
                    .isEqualTo(1L);
            }),
            dynamicTest("1번 공고 즐겨찾기 취소", () -> {
                deleteFavorite(1L);

                NoticeResponses noticeResponses = searchFavorite(userId);
                assertThat(noticeResponses.getNoticeResponses().size())
                    .isEqualTo(0);
            }),
            dynamicTest("여러 공고 즐겨찾기 추가", () -> {
                addFavorite(1L);
                addFavorite(2L);
                addFavorite(3L);
            }),
            dynamicTest("유저가 추가한 즐겨찾기 갯수 확인", () -> {
                NoticeResponses noticeResponses = searchFavorite(userId);
                assertThat(noticeResponses.getNoticeResponses().size())
                    .isEqualTo(3);
            })
        );
    }

    private void deleteFavorite(Long objectId) {
        delete(NOTICE_FAVORITE_URI + "&objectId=" + objectId);
    }

    private NoticeResponses searchFavorite(Long userId) {
        return get(NOTICE_FAVORITE_URI + "&userId=" + userId, NoticeResponses.class);
    }

    private void addFavorite(long noticeId) {
        post(NOTICE_FAVORITE_URI + "&objectId=" + noticeId);
    }

}
