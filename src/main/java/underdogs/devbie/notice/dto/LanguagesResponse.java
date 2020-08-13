package underdogs.devbie.notice.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LanguagesResponse {

    private Map<String, String> languages;

    public static LanguagesResponse from(Map<String, String> languages) {
        return new LanguagesResponse(new HashMap<>(languages));
    }
}
