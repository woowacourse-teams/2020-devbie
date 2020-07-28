package underdogs.devbie.recommendation.dto;

import static java.util.stream.Collectors.*;
import static underdogs.devbie.recommendation.domain.RecommendationType.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import underdogs.devbie.recommendation.domain.Recommendation;
import underdogs.devbie.recommendation.domain.RecommendationType;

@AllArgsConstructor
@Getter
public class RecommendationCountResponse {

    private Long recommendedCount;
    private Long nonRecommendedCount;

    public static RecommendationCountResponse fromRecommendation(List<? extends Recommendation> recommendations) {
        Map<RecommendationType, Long> counting = recommendations
            .stream()
            .collect(Collectors.groupingBy(Recommendation::getRecommendationType, summingLong(x -> 1L)));

        return new RecommendationCountResponse(nullToZero(counting.get(RECOMMENDED)),
            nullToZero(counting.get(NON_RECOMMENDED)));
    }

    private static long nullToZero(Long number) {
        if (Objects.isNull(number)) {
            return 0L;
        }
        return number;
    }
}
