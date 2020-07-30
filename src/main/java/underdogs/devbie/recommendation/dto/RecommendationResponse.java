package underdogs.devbie.recommendation.dto;

import static java.util.stream.Collectors.*;
import static underdogs.devbie.recommendation.domain.RecommendationType.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.recommendation.domain.QuestionRecommendation;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class RecommendationResponse {

    private String recommendationType;

    public static RecommendationResponse from(Optional<Recommendation> recommendation) {
        String recommendationType = recommendation
            .map(r -> r.getRecommendationType().name())
            .orElse("NOT_EXIST");

        return new RecommendationResponse(recommendationType);
    }
}
