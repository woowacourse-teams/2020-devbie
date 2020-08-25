package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChatNameFactoryTest {

    @DisplayName("중복되지 않은 ChatName 반환")
    @Test
    void createNonOverlappingName() {
        Set<ChatName> chatNames = new HashSet<>();
        chatNames.add(ChatName.of(Adjective.돌리는, Noun.원숭이, TitleColor.AMBER));
        ChatName actual = ChatNameFactory.createNonOverlappingName(ChatNames.from(chatNames));

        assertThat(actual).isNotNull();
        assertThat(actual.getChatName()).isNotEqualTo("돌리는 원숭이");
    }
}
