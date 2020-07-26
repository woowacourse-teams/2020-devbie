package underdogs.devbie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public abstract class MvcTest {

    private static final String AUTH_HEADER = "Authorization";

    @Autowired
    protected MockMvc mockMvc;

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
}
