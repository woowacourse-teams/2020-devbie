package underdogs.devbie.notice.vo;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Language;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LanguagesResponse {

    private static final LanguagesResponse languagesResponse = Arrays.stream(Language.values())
        .map(LanguagePair::from)
        .collect(collectingAndThen(toList(), LanguagesResponse::new));

    private List<LanguagePair> languages;

    public static LanguagesResponse get() {
        return languagesResponse;
    }
}
