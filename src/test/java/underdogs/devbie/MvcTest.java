package underdogs.devbie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public abstract class MvcTest {
    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions getAction(String url, String inputJson, String bearerToken) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .header("authorization", bearerToken)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions postAction(String url, String inputJson, String bearerToken) throws Exception {
        return this.mockMvc
                .perform(post(url)
                        .header("authorization", bearerToken)
                        .content(inputJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
