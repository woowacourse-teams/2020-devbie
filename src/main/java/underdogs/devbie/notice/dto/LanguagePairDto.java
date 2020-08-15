package underdogs.devbie.notice.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Language;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LanguagePairDto {

    private Map<String, String> pair;

    public static LanguagePairDto from(Language language) {
        Map<String, String> map = new HashMap<>();

        map.put("key", language.name());
        map.put("text", language.getText());

        return new LanguagePairDto(map);
    }
}
