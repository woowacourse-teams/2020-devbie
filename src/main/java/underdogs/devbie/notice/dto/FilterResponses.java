package underdogs.devbie.notice.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.vo.JobPositionPair;
import underdogs.devbie.notice.vo.LanguagePair;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FilterResponses {

    private static final FilterResponses filterResponses;

    static {
        List<LanguagePair> languages = Arrays.stream(Language.values())
            .map(LanguagePair::from)
            .collect(Collectors.toList());

        List<JobPositionPair> jobPositions = Arrays.stream(JobPosition.values())
            .map(JobPositionPair::from)
            .collect(Collectors.toList());

        filterResponses = new FilterResponses(languages, jobPositions);
    }

    private List<LanguagePair> languages;
    private List<JobPositionPair> jobPositions;

    public static FilterResponses get() {
        return filterResponses;
    }
}
