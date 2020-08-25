package underdogs.devbie.notice.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Language;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class LanguagePair {

    private Map<String, String> pair;

    public static LanguagePair from(Language language) {
        Map<String, String> map = new HashMap<>();

        map.put("key", language.name());
        map.put("text", language.getText());

        return new LanguagePair(map);
    }
}
