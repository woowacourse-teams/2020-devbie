package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChatNameFactoryTest {

    @Test
    void fetchNonRedundantName() {
        Set<ChatName> chatNames = new HashSet<>();
        chatNames.add(ChatName.of(Adjective.돌리는, Noun.원숭이));
        ChatName actual = ChatNameFactory.createNonOverlappingName(chatNames);

        assertThat(actual).isNotNull();
        assertThat(actual.getChatName()).isNotEqualTo("돌리는 원숭이");
    }
}
