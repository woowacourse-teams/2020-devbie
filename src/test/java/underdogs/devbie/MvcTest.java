package underdogs.devbie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

public abstract class MvcTest {

    public static final String AUTH_HEADER = "Authorization";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    void setUpEncoding() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    protected ResultActions getAction(String url) throws Exception {
        return this.mockMvc
            .perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions getAction(String url, String bearerToken) throws Exception {
        return this.mockMvc
            .perform(get(url)
                .header(AUTH_HEADER, bearerToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions postAction(String url) throws Exception {
        return this.mockMvc
            .perform(post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions postAction(String url, String inputJson) throws Exception {
        return this.mockMvc
            .perform(post(url)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions postAction(String url, String inputJson, String bearerToken) throws Exception {
        return this.mockMvc
            .perform(post(url)
                .header(AUTH_HEADER, bearerToken)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions putAction(String url, String inputJson, String bearerToken) throws Exception {
        return this.mockMvc
            .perform(put(url)
                .header(AUTH_HEADER, bearerToken)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions patchAction(String url, String inputJson) throws Exception {
        return this.mockMvc
            .perform(patch(url)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions patchAction(String url, String inputJson, String bearerToken) throws Exception {
        return this.mockMvc
            .perform(patch(url)
                .header(AUTH_HEADER, bearerToken)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions deleteAction(String url, String bearerToken) throws Exception {
        return this.mockMvc
            .perform(delete(url)
                .header(AUTH_HEADER, bearerToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions deleteAction(String url) throws Exception {
        return this.mockMvc
            .perform(delete(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
