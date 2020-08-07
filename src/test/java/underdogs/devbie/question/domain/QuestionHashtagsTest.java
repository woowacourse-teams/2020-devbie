package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;
import static underdogs.devbie.question.domain.QuestionTest.*;

import java.util.LinkedHashSet;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

class QuestionHashtagsTest {

    @DisplayName("삭제할 QuestionHashtag id 계산")
    @Test
    void findDeleteTargetIds() {
        Question question = Question.builder()
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(new LinkedHashSet<>())
            .build();

        QuestionHashtags questionHashtags = new QuestionHashtags(Sets.newSet(
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(2L).tagName(TagName.from("java")).build())
                .build(),
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(3L).tagName(TagName.from("network")).build())
                .build()
        ));

        List<QuestionHashtag> allByQuestionId = Lists.newArrayList(
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(1L).tagName(TagName.from("devbie")).build())
                .build(),
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(2L).tagName(TagName.from("java")).build())
                .build());

        List<Long> deleteTargetIds = questionHashtags.findDeleteTargetIds(allByQuestionId);
        assertThat(deleteTargetIds).contains(1L);
    }

}