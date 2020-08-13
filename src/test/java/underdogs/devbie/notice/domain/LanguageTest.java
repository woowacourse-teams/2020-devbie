package underdogs.devbie.notice.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LanguageTest {

    @DisplayName("공고 프로그래밍언어와 이름 모두 조회")
    @Test
    void getAll() {
        Map<String, String> languages = Language.getAllLanguageWithName();

        assertAll(
            () -> assertThat(languages).containsEntry(Language.JAVA.name(), Language.JAVA.getName()),
            () -> assertThat(languages).containsEntry(Language.CPP.name(), Language.CPP.getName()),
            () -> assertThat(languages).containsEntry(Language.C.name(), Language.C.getName()),
            () -> assertThat(languages).containsEntry(Language.PYTHON.name(), Language.PYTHON.getName()),
            () -> assertThat(languages).containsEntry(Language.RUBY.name(), Language.RUBY.getName())
        );
    }
}