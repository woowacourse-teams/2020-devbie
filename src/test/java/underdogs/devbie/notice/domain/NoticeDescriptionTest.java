package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class NoticeDescriptionTest {

    @DisplayName("NoticeDetail 생성 테스트 - 프로그래밍 언어가 없는 경우 예외 발생")
    @Test
    void constructorWithoutLanguage() {
        assertThatThrownBy(() -> new NoticeDescription(new HashSet<>(), "가족같은 회사입니다."))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeDetail 생성 테스트 - 설명이 없는경우 예외 발생")
    @Test
    void constructorWithoutDescription() {
        assertThatThrownBy(() -> new NoticeDescription(
            Stream.of(Language.CPP).collect(Collectors.toSet()), ""))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("비교 연산 테스트")
    @Test
    void equals() {
        HashSet<Language> languages = new HashSet<>();
        languages.add(Language.valueOf("JAVA"));
        NoticeDescription noticeDescription1 = new NoticeDescription(languages, "가족같은 회사입니다.");
        NoticeDescription noticeDescription2 = new NoticeDescription(languages, "가족같은 회사입니다.");

        assertThat(noticeDescription1).isEqualTo(noticeDescription2);
    }
}
