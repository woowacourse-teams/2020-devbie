package underdogs.devbie.question.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.domain.QuestionContentTest.*;
import static underdogs.devbie.question.domain.QuestionTitleTest.*;
import static underdogs.devbie.user.domain.UserTest.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.OrderBy;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionContent;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtags;
import underdogs.devbie.question.domain.QuestionTitle;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.domain.repository.QuestionRepository;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionPageRequest;
import underdogs.devbie.question.dto.QuestionReadRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.exception.NotMatchedQuestionAuthorException;
import underdogs.devbie.question.exception.QuestionNotExistedException;
import underdogs.devbie.recommendation.domain.RecommendationType;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    public static final Set<String> TEST_HASHTAGS = Sets.newSet("java", "network");

    private QuestionService questionService;

    @Mock
    private UserService userService;

    @Mock
    private QuestionHashtagService questionHashtagService;

    @Mock
    private QuestionRepository questionRepository;

    private User user;

    private Question question;

    private Set<QuestionHashtag> questionHashtags;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(userService, questionHashtagService, questionRepository);

        user = User.builder()
            .id(1L)
            .name(TEST_NAME)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(QuestionTitle.from(TEST_QUESTION_TITLE))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        questionHashtags = Sets.newSet(
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(1L).tagName(TagName.from("java")).build())
                .build(),
            QuestionHashtag.builder()
                .question(question)
                .hashtag(Hashtag.builder().id(2L).tagName(TagName.from("network")).build())
                .build()
        );

        question.setHashtags(QuestionHashtags.from(questionHashtags));
    }

    @DisplayName("질문 생성")
    @Test
    void save() {
        QuestionCreateRequest request = QuestionCreateRequest.builder()
            .title(TEST_QUESTION_TITLE)
            .content(TEST_QUESTION_CONTENT)
            .hashtags(TEST_HASHTAGS)
            .build();
        given(questionRepository.save(any(Question.class))).willReturn(question);

        Long questionId = questionService.save(user.getId(), request);

        assertThat(questionId).isEqualTo(question.getId());
    }

    @DisplayName("질문 목록 조회")
    @Test
    void readAll() {
        Page<Question> questions = new PageImpl<>(Lists.newArrayList(question));
        given(questionRepository.findAllBy(anyString(), anyString(), any(Pageable.class))).willReturn(questions);

        QuestionReadRequest questionReadRequest = QuestionReadRequest.builder().title("").content("").build();
        QuestionPageRequest questionPageRequest = new QuestionPageRequest(1, OrderBy.CREATED_DATE);
        QuestionResponses responses = questionService.readAll(questionReadRequest, questionPageRequest.toPageRequest());

        QuestionResponse response = responses.getQuestions().get(0);
        assertAll(
            () -> assertThat(response.getId()).isEqualTo(question.getId()),
            () -> assertThat(response.getVisits()).isEqualTo(question.getVisits().getVisitCount()),
            () -> assertThat(response.getTitle()).isEqualTo(question.getTitle().getTitle()),
            () -> assertThat(response.getContent()).isEqualTo(question.getContent().getContent()),
            () -> assertThat(response.getHashtags()).contains(
                HashtagResponse.builder().id(1L).tagName("java").build(),
                HashtagResponse.builder().id(2L).tagName("network").build())
        );
    }

    @DisplayName("질문 상세 조회")
    @Test
    void read() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        given(userService.findById(anyLong())).willReturn(user);

        QuestionResponse response = questionService.read(1L, true);

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(question.getId()),
            () -> assertThat(response.getAuthor()).isEqualTo(user.getName()),
            () -> assertThat(response.getVisits()).isEqualTo(question.getVisits().getVisitCount()),
            () -> assertThat(response.getTitle()).isEqualTo(question.getTitle().getTitle()),
            () -> assertThat(response.getContent()).isEqualTo(question.getContent().getContent()),
            () -> assertThat(response.getHashtags()).contains(
                HashtagResponse.builder().id(1L).tagName("java").build(),
                HashtagResponse.builder().id(2L).tagName("network").build())
        );
    }

    @DisplayName("존재하지 않는 질문 예외처리")
    @Test
    void read_When_Invalid_Hashtag_Should_Throw_HashtagNotExistedException() {

        given(questionRepository.findById(anyLong())).willThrow(new QuestionNotExistedException());

        assertThatThrownBy(() -> questionService.read(1L, false))
            .isInstanceOf(QuestionNotExistedException.class)
            .hasMessageContaining("존재하지 않는 질문입니다.");

    }

    @DisplayName("질문 수정 실패 - 질문 작성자가 아닐 경우 예외 발생")
    @Test
    void updateFailWithoutLogin() {
        QuestionUpdateRequest request = QuestionUpdateRequest.builder()
            .title("Changed Title")
            .content("Changed Content")
            .build();
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        assertThatThrownBy(() -> questionService.update(2L, 1L, request))
            .isInstanceOf(NotMatchedQuestionAuthorException.class);
    }

    @DisplayName("질문 수정")
    @Test
    void update() {
        QuestionUpdateRequest request = QuestionUpdateRequest.builder()
            .title("Changed Title")
            .content("Changed Content")
            .hashtags(Sets.newSet("kotlin"))
            .build();
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        given(userService.findById(anyLong())).willReturn(user);

        questionService.update(1L, 1L, request);

        QuestionResponse response = questionService.read(1L, true);
        assertAll(
            () -> assertThat(response.getTitle()).isEqualTo(request.getTitle()),
            () -> assertThat(response.getContent()).isEqualTo(request.getContent())
        );
        verify(questionHashtagService).updateHashtags(any(), any());
    }

    @DisplayName("질문 삭제 실패 - 질문 작성자가 아닐 경우 예외 발생")
    @Test
    void deleteFailWithoutLogin() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        assertThatThrownBy(() -> questionService.delete(User.builder().id(10L).build(), 1L))
            .isInstanceOf(NotMatchedQuestionAuthorException.class);
    }

    @DisplayName("질문 삭제")
    @Test
    void delete() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        questionService.delete(user, 1L);

        verify(questionRepository).findById(eq(1L));
    }

    @DisplayName("질문 검색 - 제목에 포함된 키워드")
    @Test
    void searchByTitle() {
        Question question1 = Question.builder()
            .userId(1L)
            .title(QuestionTitle.from("스택과 큐의 차이"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        Question question2 = Question.builder()
            .userId(2L)
            .title(QuestionTitle.from("오버스택플로우"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        Page<Question> questions = new PageImpl<>(Lists.newArrayList(question1, question2));
        given(questionRepository.findAllBy(anyString(), anyString(), any(Pageable.class))).willReturn(questions);

        QuestionReadRequest questionReadRequest = QuestionReadRequest.builder().title("스택").content("").build();
        QuestionPageRequest questionPageRequest = new QuestionPageRequest(1, OrderBy.CREATED_DATE);
        QuestionResponses responses = questionService.readAll(questionReadRequest, questionPageRequest.toPageRequest());

        assertAll(
            () -> assertThat(responses.getQuestions().get(0).getTitle()).isEqualTo("스택과 큐의 차이"),
            () -> assertThat(responses.getQuestions().get(1).getTitle()).isEqualTo("오버스택플로우")
        );
    }

    @DisplayName("해시태그로 질문 목록 필터링")
    @Test
    void searchByHashtag() {
        Question question1 = Question.builder()
            .id(100L)
            .userId(1L)
            .title(QuestionTitle.from(TEST_QUESTION_TITLE))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(questionHashtags))
            .build();
        given(questionHashtagService.findIdsByHashtagName(anyString())).willReturn(Lists.newArrayList(100L));
        given(questionRepository.findAllById(anyCollection())).willReturn(Lists.newArrayList(question1));

        QuestionResponses responses = questionService.searchByHashtag("java");

        assertAll(
            () -> assertThat(responses.getQuestions().get(0).getId()).isEqualTo(100L),
            () -> assertThat(responses.getQuestions().get(0).getTitle()).isEqualTo(TEST_QUESTION_TITLE),
            () -> assertThat(responses.getQuestions().get(0).getContent()).isEqualTo(TEST_QUESTION_CONTENT)
        );
    }

    @DisplayName("질문 아이디로 질문 목록 조회")
    @Test
    void findAllByIds() {
        Question question1 = Question.builder()
            .userId(1L)
            .title(QuestionTitle.from("스택과 큐의 차이"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        Question question2 = Question.builder()
            .userId(2L)
            .title(QuestionTitle.from("오버스택플로우"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(QuestionHashtags.from(new LinkedHashSet<>()))
            .build();

        List<Question> questions = Lists.newArrayList(question1, question2);

        given(questionRepository.findAllById(any())).willReturn(questions);

        QuestionResponses responses = questionService.findAllByIds(Lists.newArrayList(1L, 2L));

        assertAll(
            () -> assertThat(responses.getQuestions().get(0).getTitle()).isEqualTo("스택과 큐의 차이"),
            () -> assertThat(responses.getQuestions().get(1).getTitle()).isEqualTo("오버스택플로우")
        );
    }

    @DisplayName("추천, 비추천 수 증가")
    @Test
    void increaseCount() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        questionService.toggleCount(question.getId(), RecommendationType.RECOMMENDED, false);

        assertThat(question.getRecommendationCount().getRecommendedCount()).isEqualTo(1);
    }

    @DisplayName("추천, 비추천 수 토글")
    @Test
    void toggleCount() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        questionService.toggleCount(question.getId(), RecommendationType.RECOMMENDED, false);
        questionService.toggleCount(question.getId(), RecommendationType.NON_RECOMMENDED, true);

        assertAll(
            () -> assertThat(question.getRecommendationCount().getRecommendedCount()).isZero(),
            () -> assertThat(question.getRecommendationCount().getNonRecommendedCount()).isEqualTo(1)
        );
    }

    @DisplayName("추천, 비추천 수 감소")
    @Test
    void decreaseCount() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        questionService.toggleCount(question.getId(), RecommendationType.RECOMMENDED, false);
        questionService.decreaseCount(question.getId(), RecommendationType.RECOMMENDED);

        assertThat(question.getRecommendationCount().getRecommendedCount()).isZero();
    }
}
