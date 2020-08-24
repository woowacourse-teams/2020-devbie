package underdogs.devbie.chat.domain;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChatName {

    private String chatName;

    @Enumerated(value = EnumType.STRING)
    private TitleColor color;

    public static ChatName of(Adjective adjective, Noun noun, TitleColor titleColor) {
        return new ChatName(String.format("%s %s", adjective.name(), noun.name()), titleColor);
    }

    public static ChatName of(String name, TitleColor titleColor) {
        return new ChatName(name, titleColor);
    }

    public void setColor(TitleColor color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChatName chatName1 = (ChatName)o;
        return Objects.equals(chatName, chatName1.chatName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatName);
    }
}
