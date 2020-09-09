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
import underdogs.devbie.answer.domain.AnswerRepository;
import underdogs.devbie.answer.dto.AnswerCreateRequest;
import underdogs.devbie.answer.dto.AnswerResponse;
import underdogs.devbie.answer.dto.AnswerResponses;
import underdogs.devbie.answer.dto.AnswerUpdateRequest;
import underdogs.devbie.answer.exception.AnswerNotExistedException;
import underdogs.devbie.answer.exception.NotMatchedAnswerAuthorException;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long QUESTION_ID = 1L;

    private AnswerService answerService;

    @Mock
    private UserService userService;

    @Mock
    private AnswerRepository answerRepository;

    private User user;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService(userService, answerRepository);

        user = User.builder()
            .id(USER_ID)
            .name(TEST_NAME)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();
    }

    @DisplayName("면접 답변 저장")
    @Test
    void save() {
        Answer expected = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(QUESTION_ID)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        AnswerCreateRequest answerCreateRequest = AnswerCreateRequest.of(QUESTION_ID, TEST_ANSWER_CONTENT);
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
        given(userService.findById(anyLong())).willReturn(user);

        AnswerResponses answerResponses = answerService.readAll();

        assertThat(answerResponses).isNotNull();
        assertThat(answerResponses.getAnswerResponses()).isNotNull();
        List<AnswerResponse> actual = answerResponses.getAnswerResponses();
        assertAll(
            () -> assertThat(actual.get(0).getId()).isEqualTo(1L),
            () -> assertThat(actual.get(0).getAuthor()).isEqualTo(TEST_NAME),
            () -> assertThat(actual.get(0).getQuestionId()).isEqualTo(3L),
            () -> assertThat(actual.get(0).getContent()).isEqualTo(TEST_ANSWER_CONTENT)
        );
    }

    @DisplayName("질문 아이디로 답변 전체 조회")
    @Test
    void readAllByQuestionId() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(2L)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findByQuestionIdOrderByRecommendationCount(anyLong())).willReturn(Collections.singletonList(expectAnswer));
        given(userService.findById(anyLong())).willReturn(user);

        AnswerResponses answerResponses = answerService.readByQuestionId(expectAnswer.getQuestionId());

        assertThat(answerResponses).isNotNull();
        assertThat(answerResponses.getAnswerResponses()).isNotNull();
        List<AnswerResponse> actual = answerResponses.getAnswerResponses();
        assertAll(
            () -> assertThat(actual.get(0).getId()).isEqualTo(1L),
            () -> assertThat(actual.get(0).getAuthor()).isEqualTo(TEST_NAME),
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
        given(userService.findById(anyLong())).willReturn(user);

        AnswerResponse actual = answerService.read(1L);

        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(1L),
            () -> assertThat(actual.getAuthor()).isEqualTo(TEST_NAME),
            () -> assertThat(actual.getQuestionId()).isEqualTo(3L),
            () -> assertThat(actual.getContent()).isEqualTo(TEST_ANSWER_CONTENT)
        );
    }

    @DisplayName("하나의 면접 답 조회 실패 - 존재하지 않는 Answer")
    @Test
    void readFailCauseByNotExistedAnswer() {
        given(answerRepository.findById(anyLong())).willThrow(AnswerNotExistedException.class);

        assertThatThrownBy(() -> answerService.read(1L));
    }

    @DisplayName("면접 답변 수정")
    @Test
    void update() {
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from(changedAnswerContent);
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(changedAnswerContent))
            .build();
        given(answerRepository.findById(anyLong()))
            .willReturn(Optional.of(expectAnswer));
        given(userService.findById(anyLong())).willReturn(user);

        Long requestedUpdateRequestId = 1L;
        answerService.update(user, requestedUpdateRequestId, answerUpdateRequest);

        AnswerResponse updatedAnswer = answerService.read(requestedUpdateRequestId);
        assertAll(
            () -> assertEquals(updatedAnswer.getId(), requestedUpdateRequestId),
            () -> assertEquals(updatedAnswer.getAuthor(), user.getName()),
            () -> assertEquals(updatedAnswer.getQuestionId(), expectAnswer.getQuestionId()),
            () -> assertEquals(updatedAnswer.getContent(), changedAnswerContent)
        );
    }

    @DisplayName("면접 답변 수정 실패 - 존재 하지 않는 answerId")
    @Test
    void updateFailCauseByNotExistedAnswer() {
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from(changedAnswerContent);
        given(answerRepository.findById(anyLong())).willThrow(AnswerNotExistedException.class);

        assertThatThrownBy(() -> answerService.update(user, 30L, answerUpdateRequest))
            .isInstanceOf(AnswerNotExistedException.class);
    }

    @DisplayName("면접 답변 수정 실패 - 권한 없는 요청")
    @Test
    void updateFailCauseByNoAuthor() {
        String changedAnswerContent = "Changed Answer Content";
        AnswerUpdateRequest answerUpdateRequest = AnswerUpdateRequest.from(changedAnswerContent);
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(changedAnswerContent))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> answerService.update(user, 1L, answerUpdateRequest)).isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 실패 - 권한 없는 요청")
    @Test
    void deleteFailCauseByNoAuthor() {
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> answerService.delete(user, 1L))
            .isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 실패 - 존재하지 않는 면접 답변")
    @Test
    void deleteFailCauseByNotExistedAnswer() {
        Long anotherUserId = USER_ID + 1;
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(anotherUserId)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        assertThatThrownBy(() -> answerService.delete(user, 30L))
            .isInstanceOf(NotMatchedAnswerAuthorException.class);
    }

    @DisplayName("면접 답변 삭제 성공")
    @Test
    void delete() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        answerService.delete(user, 1L);

        verify(answerRepository).findById(eq(1L));
        verify(answerRepository).deleteById(expectAnswer.getId());
    }

    @DisplayName("추천, 비추천 수 증가")
    @Test
    void increaseCount() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        answerService.toggleCount(expectAnswer.getId(), RecommendationType.RECOMMENDED, false);

        assertThat(expectAnswer.getRecommendationCount().getRecommendedCount()).isEqualTo(1);
    }

    @DisplayName("추천, 비추천 수 토글")
    @Test
    void toggleCount() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        answerService.toggleCount(expectAnswer.getId(), RecommendationType.RECOMMENDED, false);
        answerService.toggleCount(expectAnswer.getId(), RecommendationType.NON_RECOMMENDED, true);

        assertAll(
            () -> assertThat(expectAnswer.getRecommendationCount().getRecommendedCount()).isZero(),
            () -> assertThat(expectAnswer.getRecommendationCount().getNonRecommendedCount()).isEqualTo(1)
        );
    }

    @DisplayName("추천, 비추천 수 감소")
    @Test
    void decreaseCount() {
        Answer expectAnswer = Answer.builder()
            .id(1L)
            .userId(USER_ID)
            .questionId(3L)
            .content(AnswerContent.from(TEST_ANSWER_CONTENT))
            .build();
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(expectAnswer));

        answerService.toggleCount(expectAnswer.getId(), RecommendationType.RECOMMENDED, false);
        answerService.decreaseCount(expectAnswer.getId(), RecommendationType.RECOMMENDED);

        assertThat(expectAnswer.getRecommendationCount().getRecommendedCount()).isZero();
    }
}
