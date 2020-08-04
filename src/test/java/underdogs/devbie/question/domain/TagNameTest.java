package underdogs.devbie.question.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TagNameTest {

    public static final String TEST_HASHTAG_NAME = "Test TagName";

    @DisplayName("TagName - from")
    @Test
    void from() {
        TagName tagName = TagName.from(TEST_HASHTAG_NAME);

        assertThat(tagName.getName()).isEqualTo(TEST_HASHTAG_NAME);
    }

    @DisplayName("TagName name - eqauls")
    @Test
    void equals() {
        TagName tagName = TagName.from(TEST_HASHTAG_NAME);
        TagName anotherTagName = TagName.from(TEST_HASHTAG_NAME);

        assertThat(tagName).isEqualTo(anotherTagName);
    }

}