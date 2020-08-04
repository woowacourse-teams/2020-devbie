package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static underdogs.devbie.question.domain.QuestionTest.*;
import static underdogs.devbie.question.domain.TagNameTest.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import underdogs.devbie.exception.CreateFailException;

class QuestionHashtagTest {

    @DisplayName("QuestionHashtag 빌더 테스트 - Question이 없을 경우 예외 발생")
    @Test
    void questionHashtagBuilderWithoutQuestion() {
        Hashtag hashTag = Hashtag.builder()
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();

        assertThatThrownBy(() -> QuestionHashtag.builder()
            .hashtag(hashTag)
            .build())
            .isInstanceOf(CreateFailException.class);
    }

    @DisplayName("QuestionHashtag 빌더 테스트 - Hashtag가 없을 경우 예외 발생")
    @Test
    void questionHashtagBuilderWithoutHashtag() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .build();

        assertThatThrownBy(() -> QuestionHashtag.builder()
            .question(question)
            .build())
            .isInstanceOf(CreateFailException.class);
    }
}