package underdogs.devbie.notice.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.JobPosition;
import underdogs.devbie.notice.domain.Notice;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class NoticeResponse {

    private Long id;
    private String name;
    private Set<String> languages;
    private JobPosition jobPosition;
    private String image;

    public static List<NoticeResponse> toList(List<Notice> notices) {
        return notices.stream()
            .map(notice -> NoticeResponse.builder()
                .id(notice.getId())
                .name(notice.getCompanyName())
                .image(notice.getImage())
                .languages(notice.getLanguages())
                .jobPosition(notice.getJobPosition())
                .build())
            .collect(Collectors.toList());
    }
}
