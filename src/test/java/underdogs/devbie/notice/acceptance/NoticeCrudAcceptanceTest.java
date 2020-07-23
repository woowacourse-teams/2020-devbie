package underdogs.devbie.notice.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NoticeCrudAcceptanceTest extends NoticeAcceptanceTest {
    // Feature: 공고 관리
    //
    //  Scenario: 공고를 관리한다.
    //      When 공고 1개를 추가 요청한다.
    //      Then 공고가 업로드 되었다.
    //
    //      When 공고 전체를 조회 요청한다.
    //      Then 공고 전체가 조회된다.
    //
    //      When 공고를 수정한다.
    //      Then 공고가 수정 되었다.
    //
    //      When 공고를 삭제한다.
    //      Then 공고가 삭제되었다.
    @DisplayName("공고 인수테스트")
    @Test
    void notice() {
        createNotice();
        readAllNotice();
        updateNotice();
        readNoticeDetail();
        deleteNotice();
    }
}
