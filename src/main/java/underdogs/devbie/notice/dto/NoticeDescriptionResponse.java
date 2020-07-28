package underdogs.devbie.notice.dto;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeDescription;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class NoticeDescriptionResponse {

    private Set<String> languages;
    private String content;

    public static NoticeDescriptionResponse from(NoticeDescription noticeDescription) {
        Set<String> languages = noticeDescription.getLanguages()
            .stream()
            .map(Language::getName)
            .collect(Collectors.toSet());

        return new NoticeDescriptionResponse(languages, noticeDescription.getContent());
    }
}