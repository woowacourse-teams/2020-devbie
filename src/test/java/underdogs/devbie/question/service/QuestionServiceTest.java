package underdogs.devbie.question.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static underdogs.devbie.question.acceptance.QuestionAcceptanceTest.*;
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

import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.OrderBy;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.question.domain.QuestionContent;
import underdogs.devbie.question.domain.QuestionHashtag;
import underdogs.devbie.question.domain.QuestionHashtags;
import underdogs.devbie.question.domain.QuestionRepository;
import underdogs.devbie.question.domain.QuestionTitle;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.QuestionCreateRequest;
import underdogs.devbie.question.dto.QuestionResponse;
import underdogs.devbie.question.dto.QuestionResponses;
import underdogs.devbie.question.dto.QuestionUpdateRequest;
import underdogs.devbie.question.exception.NotMatchedQuestionAuthorException;
import underdogs.devbie.user.domain.User;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    public static final Set<String> TEST_HASHTAGS = Sets.newSet("java", "network");

    private QuestionService questionService;

    @Mock
    private QuestionHashtagService questionHashtagService;

    @Mock
    private QuestionRepository questionRepository;

    private User user;

    private Question question;

    private Set<QuestionHashtag> questionHashtags;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionHashtagService, questionRepository);

        user = User.builder()
            .id(1L)
            .oauthId(TEST_OAUTH_ID)
            .email(TEST_USER_EMAIL)
            .build();

        question = Question.builder()
            .id(1L)
            .userId(1L)
            .title(QuestionTitle.from(TEST_QUESTION_TITLE))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(new LinkedHashSet<>())
            .build();

        questionHashtags = Sets.newSet(
            QuestionHashtag.builder().question(question).hashtag(Hashtag.builder().id(1L).tagName(TagName.from("java")).build()).build(),
            QuestionHashtag.builder().question(question).hashtag(Hashtag.builder().id(2L).tagName(TagName.from("network")).build()).build()
        );

        question.setHashtags(new QuestionHashtags(questionHashtags));
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
        List<Question> questions = Lists.newArrayList(question);
        given(questionRepository.findAll()).willReturn(questions);

        QuestionResponses responses = questionService.readAllOrderBy(OrderBy.CREATED_DATE);

        QuestionResponse response = responses.getQuestions().get(0);
        assertAll(
            () -> assertThat(response.getQuestionId()).isEqualTo(question.getId()),
            () -> assertThat(response.getUserId()).isEqualTo(question.getUserId()),
            () -> assertThat(response.getVisits()).isEqualTo(question.getVisits().getVisitCount()),
            () -> assertThat(response.getTitle()).isEqualTo(question.getTitle().getTitle()),
            () -> assertThat(response.getContent()).isEqualTo(question.getContent().getContent()),
            () -> assertThat(response.getHashtags()).contains(
                HashtagResponse.builder().id(1L).tagName("java").build(),
                HashtagResponse.builder().id(2L).tagName("network").build())
        );
    }

    // TODO: 질문 목록 조회, 최신 순 and 조회 순

    @DisplayName("질문 상세 조회")
    @Test
    void read() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        QuestionResponse response = questionService.read(1L);

        assertAll(
            () -> assertThat(response.getQuestionId()).isEqualTo(question.getId()),
            () -> assertThat(response.getUserId()).isEqualTo(question.getUserId()),
            () -> assertThat(response.getVisits()).isEqualTo(question.getVisits().getVisitCount()),
            () -> assertThat(response.getTitle()).isEqualTo(question.getTitle().getTitle()),
            () -> assertThat(response.getContent()).isEqualTo(question.getContent().getContent()),
            () -> assertThat(response.getHashtags()).contains(
                HashtagResponse.builder().id(1L).tagName("java").build(),
                HashtagResponse.builder().id(2L).tagName("network").build())
        );
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

        questionService.update(1L, 1L, request);

        QuestionResponse response = questionService.read(1L);
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

        assertThatThrownBy(() -> questionService.delete(2L, 1L))
            .isInstanceOf(NotMatchedQuestionAuthorException.class);
    }

    @DisplayName("질문 삭제")
    @Test
    void delete() {
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));

        questionService.delete(1L, 1L);

        verify(questionRepository).findById(eq(1L));
    }

    @DisplayName("질문 검색 - 제목에 포함된 키워드")
    @Test
    void searchByTitle() {
        Question question1 = Question.builder()
            .userId(1L)
            .title(QuestionTitle.from("스택과 큐의 차이"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(new LinkedHashSet<>())
            .build();

        Question question2 = Question.builder()
            .userId(2L)
            .title(QuestionTitle.from("오버스택플로우"))
            .content(QuestionContent.from(TEST_QUESTION_CONTENT))
            .hashtags(new LinkedHashSet<>())
            .build();

        List<Question> questions = Lists.newArrayList(question1, question2);

        given(questionRepository.findByTitleLike(anyString())).willReturn(questions);

        QuestionResponses responses = questionService.searchByTitle("스택");

        assertAll(
            () -> assertThat(responses.getQuestions().get(0).getTitle()).isEqualTo("스택과 큐의 차이"),
            () -> assertThat(responses.getQuestions().get(1).getTitle()).isEqualTo("오버스택플로우")
        );
    }

    // TODO: searchByHashtag test
}
