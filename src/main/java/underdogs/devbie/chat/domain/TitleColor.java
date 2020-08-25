package underdogs.devbie.chat.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.chat.exception.NoSuchTitleColorException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public enum TitleColor {

    RED_ORANGE("#F44336"),
    RUBY("#E91E63"),
    DARK_ORCHID("#9C27B0"),
    PURPLE_HEART("#673AB7"),
    FREE_SPEECH_BLUE("#3F51B5"),
    DODGER_BLUE("#2196F3"),
    DART_CYAN("#009688"),
    FRUIT_SALAD("#4CAF50"),
    AMBER("#FFC107"),
    ORANGE_PEEL("#FF9800"),
    BAROSSA("#4D2F40"),
    IRONSTONE("#795548"),
    NOBEL("#9E9E9E");

    private String color;

    public static TitleColor from(String input) {
        return Arrays.stream(values())
            .filter(color -> color.color.equals(input))
            .findFirst()
            .orElseThrow(NoSuchTitleColorException::new);
    }
}
