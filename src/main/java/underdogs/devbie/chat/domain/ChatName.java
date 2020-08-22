package underdogs.devbie.chat.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class ChatName {

    private String chatName;

    public static ChatName of(Verb verb, Noun noun) {
        return new ChatName(String.format("%s %s", verb.name(), noun.name()));
    }

    public static ChatName of(String name) {
        return new ChatName(name);
    }
}
