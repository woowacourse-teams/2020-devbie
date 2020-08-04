package underdogs.devbie.question.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class QuestionHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "question_id")
    private Question question;

    @ManyToOne
    @Column(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public QuestionHashtag(Long id, Question question, Hashtag hashtag) {
        validateParameters(question, hashtag);
        this.question = question;
        this.hashtag = hashtag;
    }

    private void validateParameters(Question question, Hashtag hashtag) {
        if (Objects.isNull(question) || Objects.isNull(hashtag)) {
            throw new CreateFailException();
        }
    }
}
