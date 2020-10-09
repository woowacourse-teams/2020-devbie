package underdogs.devbie.notice.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.exception.NoSuchLanguageException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public enum Language {

    C("C"),
    CPP("C++"),
    JAVA("JAVA"),
    C_SHARP("C#"),
    JAVASCRIPT("JAVASCRIPT"),
    TYPESCRIPT("TYPESCRIPT"),
    PYTHON("PYTHON"),
    RUBY("RUBY"),
    PHP("PHP"),
    SWIFT("SWIFT");

    private String text;

    public static Language from(String input) {
        return Arrays.stream(values())
            .filter(language -> language.text.equals(input))
            .findFirst()
            .orElseThrow(NoSuchLanguageException::new);
    }
}
