package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.notice.expception.CreateFailException;

class NoticeDetailTest {

    @DisplayName("NoticeDetail 생성 테스트 - 프로그래밍 언어가 없는 경우 예외 발생")
    @Test
    void constructorWithoutLanguage() {
        assertThatThrownBy(() -> new NoticeDetail(new HashSet<>(), "가족같은 회사입니다."))
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("NoticeDetail 생성 테스트 - 설명이 없는경우 예외 발생")
    @Test
    void constructorWithoutDescription() {
        assertThatThrownBy(() -> new NoticeDetail(new HashSet<>(Arrays.asList("C++")), ""))
            .isInstanceOf(CreateFailException.class);
    }
}