package underdogs.devbie.question.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.exception.CreateFailException;

@Entity
@Table(name = "question_hashtag",
    indexes = @Index(name = "i_question_hashtag", columnList = "hashtag_id, question_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuestionHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
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
