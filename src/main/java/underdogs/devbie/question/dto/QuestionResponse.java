package underdogs.devbie.question.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Question;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class QuestionResponse {

    private Long id;
    private String author;
    private Long visits;
    private Long recommendedCount;
    private Long nonRecommendedCount;
    private String title;
    private String content;
    private List<HashtagResponse> hashtags;

    public static QuestionResponse of(Question question, User author) {
        return QuestionResponse.builder()
            .id(question.getId())
            .author(author.getName())
            .visits(question.getVisits().getVisitCount())
            .recommendedCount(question.getRecommendationCount().getRecommendedCount())
            .nonRecommendedCount(question.getRecommendationCount().getNonRecommendedCount())
            .title(question.getTitle().getTitle())
            .content(question.getContent().getContent())
            .hashtags(HashtagResponse.listFrom(question.getHashtags()))
            .build();
    }

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
            .id(question.getId())
            .visits(question.getVisits().getVisitCount())
            .recommendedCount(question.getRecommendationCount().getRecommendedCount())
            .nonRecommendedCount(question.getRecommendationCount().getNonRecommendedCount())
            .title(question.getTitle().getTitle())
            .content(question.getContent().getContent())
            .hashtags(HashtagResponse.listFrom(question.getHashtags()))
            .build();
    }
}
