package underdogs.devbie.notice.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.expception.NoSuchLanguageException;

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

    private String name;

    public static Language from(String input) {
        return Arrays.stream(values())
            .filter(language -> language.name.equals(input))
            .findFirst()
            .orElseThrow(NoSuchLanguageException::new);
    }

    public static Map<String, String> getAllLanguageWithName() {
        return Arrays.stream(values())
            .collect(Collectors.toMap(Language::name, Language::getName));
    }
}
