package underdogs.devbie.answer.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnswersTest {

    @DisplayName("Answers 정적 팩토리 메서드")
    @Test
    void from() {
        Answers answers = Answers.from(Lists.newArrayList(new Answer(), new Answer(), new Answer()));

        assertThat(answers).isInstanceOf(Answers.class);
    }
}
