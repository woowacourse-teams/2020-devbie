package underdogs.devbie.question.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionContent;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtags;
import underdogs.devbie.question.domain.QuestionTitle;
import underdogs.devbie.question.domain.TagName;

@SpringBootTest
@Transactional
class QuestionHashtagRepositoryImplTest {

    @Autowired
    private QuestionHashtagRepository questionHashtagRepository;

    @Autowired
    private EntityManager entityManager;

    private Question question1;
    private Question question2;

    private QuestionHashtag questionHashtag1;
    private QuestionHashtag questionHashtag2;

    @BeforeEach
    public void setUp() {
        question1 = Question.builder()
            .id(100L)
            .userId(1L)
            .title(QuestionTitle.from("Q1"))
            .content(QuestionContent.from("QC1"))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();
        question2 = Question.builder()
            .id(101L)
            .userId(1L)
            .title(QuestionTitle.from("Q2"))
            .content(QuestionContent.from("QC2"))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        questionHashtag1 = new QuestionHashtag(1L, question1, Hashtag.builder().id(63L).tagName(
            TagName.from("java")).build());
        questionHashtag2 = new QuestionHashtag(2L, question2, Hashtag.builder().id(63L).tagName(
            TagName.from("java")).build());

        saveQuestionHashtags();
    }

    private void saveQuestionHashtags() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE question_hashtag").executeUpdate();

        questionHashtagRepository.save(questionHashtag1);
        questionHashtagRepository.save(questionHashtag2);

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @DisplayName("질문에 달린 해시태그 부분 삭제")
    @Test
    public void deleteAllByHashtagIds() {
        questionHashtagRepository.deleteAllByHashtagIds(Lists.newArrayList(63L), question1.getId());

        List<QuestionHashtag> questionHashtags = questionHashtagRepository.findAllByQuestionId(question1.getId());

        assertThat(questionHashtags).isEmpty();
    }

    @DisplayName("해시태그가 포함된 질문 검색")
    @Test
    public void findQuestionIdsByHashtagId() {
        List<Long> questionIds = questionHashtagRepository.findQuestionIdsByHashtagId(63L);

        assertAll(
            () -> assertThat(questionIds).hasSize(2),
            () -> assertThat(questionIds).contains(100L, 101L)
        );
    }
}