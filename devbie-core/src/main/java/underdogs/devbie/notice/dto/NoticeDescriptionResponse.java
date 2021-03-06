package underdogs.devbie.notice.dto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeDescription;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class NoticeDescriptionResponse {

    private Set<String> languages;
    private String content;
    private String applyUrl;

    public static NoticeDescriptionResponse from(NoticeDescription noticeDescription) {
        Set<String> languages = noticeDescription.getLanguages()
            .stream()
            .map(Language::getText)
            .collect(Collectors.toSet());

        String applyUrl = "";
        if (Objects.nonNull(noticeDescription.getApplyUrl())) {
            applyUrl = noticeDescription.getApplyUrl().getApplyUrl();
        }

        return new NoticeDescriptionResponse(languages, noticeDescription.getContent(),
            applyUrl);
    }
}
