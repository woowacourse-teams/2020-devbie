package underdogs.devbie.notice.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.Notice;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class NoticeResponses {

    private List<NoticeResponse> noticeResponses;

    public static NoticeResponses listFrom(List<Notice> notices) {
        List<NoticeResponse> response = notices.stream()
            .map(notice -> NoticeResponse.builder()
                .id(notice.getId())
                .name(notice.getCompany().getName())
                .image(notice.getImage())
                .languages(collectLanguageName(notice))
                .jobPosition(notice.getJobPosition())
                .build())
            .collect(Collectors.toList());

        return new NoticeResponses(response);
    }

    private static Set<String> collectLanguageName(Notice notice) {
        return notice.getNoticeDescription()
            .getLanguages()
            .stream()
            .map(Language::getName)
            .collect(Collectors.toSet());
    }
}
