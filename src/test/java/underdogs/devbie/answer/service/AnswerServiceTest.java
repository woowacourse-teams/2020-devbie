package underdogs.devbie.answer.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.answer.controller.AnswerControllerTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.answer.domain.AnswerContent;
import underdogs.devbie.answer.domain.repository.AnswerRepository;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;
import underdogs.devbie.answer.exception.AnswerNotExistedException;
import underdogs.devbie.answer.exception.NotMatchedAnswerAuthorException;
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

    @DisplayName("면접 답변 전체 조회")
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

    @DisplayName("하나의 면접 답 조회")
    @Test
    void read() throws Exception {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong()))
            .willReturn(Optional.of(expectAnswer));

        AnswerResponse actual = answerService.read(1L);

        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertEquals(actual.getId(), 1L),
            () -> assertEquals(actual.getUserId(), 2L),
            () -> assertEquals(actual.getQuestionId(), 3L),
            () -> assertEquals(actual.getContent(), TEST_ANSWER_CONTENT)
        );
    }

    @DisplayName("하나의 면접 답 조회 실패 - 존재하지 않는 Answer")
    @Test
    void readFailCauseByNotExistedAnswer() throws Exception {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willThrow(AnswerNotExistedException.class);

        assertThatThrownBy(
            () -> {
                answerService.read(1L);
            }
        );
    }

    @DisplayName("면접 답변 수정")
    @Test
    void update() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = new AnswerUpdateRequest(changedAnswerContent);
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(changedAnswerContent))
            .build();
        given(answerRepository.findById(anyLong()))
            .willReturn(Optional.of(expectAnswer));

        Long requestedUpdateRequestId = 1L;
        answerService.update(user, requestedUpdateRequestId, answerUpdateRequest);

        AnswerResponse updatedAnswer = answerService.read(requestedUpdateRequestId);
        assertAll(
            () -> assertEquals(updatedAnswer.getId(), requestedUpdateRequestId),
            () -> assertEquals(updatedAnswer.getUserId(), expectAnswer.getUserId()),
            () -> assertEquals(updatedAnswer.getQuestionId(), expectAnswer.getQuestionId()),
            () -> assertEquals(updatedAnswer.getContent(), changedAnswerContent)
        );
    }

    @DisplayName("면접 답변 수정 실패 - 존재 하지 않는 answerId")
    @Test
    void updateFailCauseByNotExistedAnswer() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = new AnswerUpdateRequest(changedAnswerContent);
        given(answerRepository.findById(anyLong())).willThrow(AnswerNotExistedException.class);

        assertThatThrownBy(() -> {
            answerService.update(user, 30L, answerUpdateRequest);
        }).isInstanceOf(AnswerNotExistedException.class);
    }

    @DisplayName("면접 답변 수정 실패 - 권한 없는 요청")
    @Test
    void updateFailCauseByNoAuthor() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = new AnswerUpdateRequest(changedAnswerContent);
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(changedAnswerContent))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> {
            answerService.update(user, 1L, answerUpdateRequest);
        }).isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 실패 - 권한 없는 요청")
    @Test
    void deleteFailCauseByNoAuthor() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> {
            answerService.delete(user, 1L);
        }).isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 실패 - 권한 없는 요청")
    @Test
    void deleteFailCauseByNotExistedAnswer() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> {
            answerService.delete(user, 30L);
        }).isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 성공")
    @Test
    void delete() {
        User user = User.builder()
            .id(USER_ID)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        answerService.delete(user, 1L);

        verify(answerRepository).findById(eq(1L));
        verify(answerRepository).delete(expectAnswer);
    }
}
