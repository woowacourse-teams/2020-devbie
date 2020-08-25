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
        chatNames.add(ChatName.of(Adjective.깜찍한, Animal.강아지, TitleColor.AMBER));
        ChatName actual = ChatNameFactory.createNonOverlappingName(ChatNames.from(chatNames));

        assertThat(actual).isNotNull();
        assertThat(actual.getChatName()).isNotEqualTo("깜찍한 강아지");
    }

    @DisplayName("존재하는 모든 ChatName이 존재할 때")
    @Test
    void createNonOverlappingNameWhenExistAllChatName() {
        Set<ChatName> chatNames = ChatNameFactory.getNames();
        assertThatThrownBy(() -> ChatNameFactory.createNonOverlappingName(ChatNames.from(chatNames)))
            .isInstanceOf(IndexOutOfBoundsException.class);
    }
}
