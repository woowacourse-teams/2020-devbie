package underdogs.devbie.question.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.Question;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class QuestionResponse {

    private Long questionId;
    private Long userId;
    private Long visits;
    private Long recommendedCount;
    private Long nonRecommendedCount;
    private String title;
    private String content;
    private List<HashtagResponse> hashtags;

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
            .questionId(question.getId())
            .userId(question.getUserId())
            .visits(question.getVisits().getVisitCount())
            .recommendedCount(question.getRecommendationCount().getRecommendedCount())
            .nonRecommendedCount(question.getRecommendationCount().getNonRecommendedCount())
            .title(question.getTitle().getTitle())
            .content(question.getContent().getContent())
            .hashtags(HashtagResponse.listFrom(question.getHashtags()))
            .build();
    }
}
