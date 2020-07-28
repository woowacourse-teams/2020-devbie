package underdogs.devbie.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessTokenRequest {

    private String code;
    private String client_id;
    private String client_secret;
}
