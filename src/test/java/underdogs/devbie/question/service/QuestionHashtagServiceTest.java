package underdogs.devbie.question.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.domain.QuestionTest.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtagRepository;
import underdogs.devbie.question.domain.QuestionHashtags;
import underdogs.devbie.question.domain.TagName;

@ExtendWith(MockitoExtension.class)
class QuestionHashtagServiceTest {

    private QuestionHashtagService questionHashtagService;

    @Mock
    private HashtagService hashtagService;

    @Mock
    private QuestionHashtagRepository questionHashtagRepository;

    private Question question;

    private Hashtag hashtag;

    @BeforeEach
    void setUp() {
        questionHashtagService = new QuestionHashtagService(hashtagService, questionHashtagRepository);

        question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();
        hashtag = Hashtag.builder()
            .id(1L)
            .tagName(TagName.from("java"))
            .build();
    }

    @DisplayName("질문 생성시 해시태그 함께 등록")
    @Test
    void saveHashtags() {
        given(hashtagService.findOrCreateHashtag(anyString())).willReturn(hashtag);
        given(questionHashtagRepository.save(any(QuestionHashtag.class))).willReturn(
            QuestionHashtag.builder()
                .id(1L)
                .question(question)
                .hashtag(hashtag)
                .build()
        );

        questionHashtagService.saveHashtags(question, Sets.newSet("java"));

        verify(hashtagService).findOrCreateHashtag(eq("java"));
        assertAll(
            () -> assertThat(question.getHashtags().getQuestionHashtags()).hasSize(1),
            () -> assertThat(new ArrayList<>(question.getHashtags().getQuestionHashtags()).get(0).getHashtag().getTagName())
                .isEqualTo(hashtag.getTagName())
        );
    }

    @DisplayName("질문 생성시 해시태그 업데이트")
    @Test
    void updateHashtags() {
        Hashtag updateHashtag = Hashtag.builder()
            .id(3L)
            .tagName(TagName.from("kotlin"))
            .build();
        given(hashtagService.findOrCreateHashtag(anyString())).willReturn(hashtag);
        given(questionHashtagRepository.save(any(QuestionHashtag.class))).willReturn(
            QuestionHashtag.builder()
                .id(1L)
                .question(question)
                .hashtag(updateHashtag)
                .build()
        );

        questionHashtagService.updateHashtags(question, Sets.newSet("kotlin"));

        verify(hashtagService).findOrCreateHashtag(eq("kotlin"));
        assertAll(
            () -> assertThat(question.getHashtags().getQuestionHashtags()).hasSize(1),
            () -> assertThat(new ArrayList<>(question.getHashtags().getQuestionHashtags()).get(0).getHashtag().getTagName())
                .isEqualTo(updateHashtag.getTagName())
        );
    }
}
