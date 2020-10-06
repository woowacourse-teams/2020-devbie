package underdogs.devbie.question.domain;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.question.dto.HashtagResponse;

@Document(indexName = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EsQuestion {

    @Id
    private String id;

    private String userId;

    private String title;

    private String content;

    private String visits;

    private String recommendedCount;

    private String nonRecommendedCount;

    @Field(type = FieldType.Nested)
    private List<HashtagResponse> hashtags;

    public static EsQuestion from(Question question) {
        return new EsQuestion(
            String.valueOf(question.getId()),
            String.valueOf(question.getUserId()),
            question.getTitle().getTitle(),
            question.getContent().getContent(),
            String.valueOf(question.getVisits().getVisitCount()),
            String.valueOf(question.getRecommendationCount().getRecommendedCount()),
            String.valueOf(question.getRecommendationCount().getNonRecommendedCount()),
            HashtagResponse.listFrom(question.getHashtags())
        );
    }
}
