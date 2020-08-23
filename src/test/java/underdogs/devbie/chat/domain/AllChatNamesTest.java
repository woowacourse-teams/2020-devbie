package underdogs.devbie.chat.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class AllChatNamesTest {

    @Test
    void fetchNonRedundantName() {
        Set<ChatName> chatNames = new HashSet<>();
        chatNames.add(ChatName.of(Verb.돌리는, Noun.원숭이));
        ChatName actual = AllChatNames.fetchNonRedundantName(chatNames);

        assertThat(actual).isNotNull();
        assertThat(actual.getChatName()).isNotEqualTo("돌리는 원숭이");
    }
}
