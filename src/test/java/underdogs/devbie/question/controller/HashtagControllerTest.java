package underdogs.devbie.question.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;
import static underdogs.devbie.question.domain.TagNameTest.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.question.domain.Hashtag;
import underdogs.devbie.question.domain.TagName;
import underdogs.devbie.question.dto.HashtagCreateRequest;
import underdogs.devbie.question.dto.HashtagResponse;
import underdogs.devbie.question.dto.HashtagResponses;
import underdogs.devbie.question.service.HashtagService;

@WebMvcTest(HashtagController.class)
class HashtagControllerTest extends MvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private HashtagService hashtagService;

    @MockBean
    private BearerAuthInterceptor bearerAuthInterceptor;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @BeforeEach
    void setUp() {
        given(bearerAuthInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    @DisplayName("해시태그 생성")
    @Test
    void save() throws Exception {
        HashtagCreateRequest request = HashtagCreateRequest.builder()
            .tagName("java")
            .build();
        String inputJson = objectMapper.writeValueAsString(request);

        given(hashtagService.save(any(HashtagCreateRequest.class))).willReturn(100L);

        postAction("/api/hashtags", inputJson, TEST_TOKEN)
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("location", "/api/hashtags/100"));
    }

    @DisplayName("해시태그 목록 조회")
    @Test
    void readAll() throws Exception {
        Hashtag hashtag = Hashtag.builder()
            .id(100L)
            .tagName(TagName.from(TEST_HASHTAG_NAME))
            .build();
        HashtagResponses responses = HashtagResponses.builder()
            .hashtags(Lists.newArrayList(HashtagResponse.from(hashtag)))
            .build();

        System.out.println(responses.getHashtags() + ">>>>>>");

        given(hashtagService.readAll()).willReturn(responses);

        MvcResult mvcResult = getAction("/api/hashtags")
            .andExpect(status().isOk())
            .andReturn();
        String value = mvcResult.getResponse().getContentAsString();
        HashtagResponses hashtagResponses = objectMapper.readValue(value, HashtagResponses.class);

        assertAll(
            () -> assertThat(hashtagResponses.getHashtags().get(0).getId()).isEqualTo(100L),
            () -> assertThat(hashtagResponses.getHashtags().get(0).getTagName()).isEqualTo(TEST_HASHTAG_NAME)
        );
    }
}
