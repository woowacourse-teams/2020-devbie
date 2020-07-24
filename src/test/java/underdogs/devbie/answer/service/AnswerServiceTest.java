package underdogs.devbie.answer.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.answer.controller.AnswerControllerTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.Answers;
import underdogs.devbie.answer.domain.repository.AnswerRepository;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.user.domain.User;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long QUESTION_ID = 1L;

    private AnswerService answerService;

    @Mock
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService(answerRepository);
    }

    @DisplayName("면접 답변 저장")
    @Test
    void save() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        Answer expected = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(QUESTION_ID)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(QUESTION_ID, TEST_ANSWER_CONTENT);
        given(answerRepository.save(any(Answer.class))).willReturn(expected);

        Long id = answerService.save(user, answerCreateRequest);

        assertThat(id).isEqualTo(expected.getId());
    }

    @DisplayName("면접 전체 조회")
    @Test
    void readAll() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findAll()).willReturn(Collections.singletonList(expectAnswer));

        AnswerResponses answerResponses = answerService.readAll();

        assertThat(answerResponses).isNotNull();
        assertThat(answerResponses.getAnswerResponses()).isNotNull();
        List<AnswerResponse> actual = answerResponses.getAnswerResponses();
        assertAll(
            () -> assertThat(actual.get(0).getId()).isEqualTo(1L),
            () -> assertThat(actual.get(0).getUserId()).isEqualTo(2L),
            () -> assertThat(actual.get(0).getQuestionId()).isEqualTo(3L),
            () -> assertThat(actual.get(0).getContent()).isEqualTo(TEST_ANSWER_CONTENT)
        );
    }
}
