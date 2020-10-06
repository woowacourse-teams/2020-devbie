package underdogs.devbie.question.domain;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(indexName = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EsQuestion {

    @Id
    private String id;

    private String title;

    private String content;

    public static EsQuestion from(Question question) {
        return new EsQuestion(
            String.valueOf(question.getId()),
            question.getTitle().getTitle(),
            question.getContent().getContent()
        );
    }
}
