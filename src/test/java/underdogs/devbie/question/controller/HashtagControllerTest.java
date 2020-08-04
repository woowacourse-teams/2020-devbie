package underdogs.devbie.question.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static underdogs.devbie.auth.controller.AuthControllerTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import underdogs.devbie.MvcTest;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;
import underdogs.devbie.question.dto.HashtagCreateRequest;
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
}
