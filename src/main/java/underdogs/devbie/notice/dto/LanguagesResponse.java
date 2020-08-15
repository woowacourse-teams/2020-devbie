package underdogs.devbie.notice.dto;

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

    private List<LanguagePairDto> languages;

    public static LanguagesResponse from() {
        return Arrays.stream(Language.values())
            .map(LanguagePairDto::from)
            .collect(collectingAndThen(toList(), LanguagesResponse::new));
    }
}
