package underdogs.devbie.answer.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.answer.domain.Answer;
import underdogs.devbie.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class AnswerResponse {

    private Long id;
    private Long userId;
    private String author;
    private Long questionId;
    private String content;
    private Long recommendedCount;
    private Long nonRecommendedCount;

    public static AnswerResponse of(Answer answer, User author) {
        return AnswerResponse.builder()
            .id(answer.getId())
            .userId(answer.getUserId())
            .author(author.getName())
            .questionId(answer.getQuestionId())
            .content(answer.getContent().getContent())
            .recommendedCount(answer.getRecommendationCount().getRecommendedCount())
            .nonRecommendedCount(answer.getRecommendationCount().getNonRecommendedCount())
            .build();
    }
}
