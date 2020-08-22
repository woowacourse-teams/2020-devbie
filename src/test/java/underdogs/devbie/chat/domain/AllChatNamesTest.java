package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class AllChatNamesTest {

    @Test
    void createAllChatNames() {
        List<ChatName> allChatNames = AllChatNames.createAllChatNames();

        assertAll(
            () -> assertEquals(allChatNames.size(), Noun.values().length * Verb.values().length)
        );
    }

    @Test
    void fetchNonRedundantName() {
        ArrayList<ChatName> chatNames = new ArrayList<>();
        chatNames.add(ChatName.of(Verb.돌리는, Noun.원숭이));
        ChatName actual = AllChatNames.fetchNonRedundantName(chatNames);

        assertThat(actual).isNotNull();
        assertThat(actual.getChatName()).isNotEqualTo("돌리는 원숭이");
    }
}