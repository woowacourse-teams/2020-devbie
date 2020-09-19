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
        assertThatThrownBy(() -> new NoticeDescription(new HashSet<>(), "가족같은 회사입니다.", "https://devbie.kr"))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeDetail 생성 테스트 - 설명이 없는경우 예외 발생")
    @Test
    void constructorWithoutDescription() {
        assertThatThrownBy(() -> new NoticeDescription(
            Stream.of(Language.CPP).collect(Collectors.toSet()), "", "https://devbie.kr"))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeDetail 생성 테스트 - 잘못된 URL 입력")
    @Test
    void constructorWithoutUrl() {
        assertThatThrownBy(() -> new NoticeDescription(
            Stream.of(Language.CPP).collect(Collectors.toSet()), "가족같은 회사입니다.", ""))
            .isInstanceOf(CreateFailException.class);
    }
}
