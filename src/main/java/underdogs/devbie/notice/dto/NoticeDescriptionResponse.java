package underdogs.devbie.notice.dto;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.domain.Language;
import underdogs.devbie.notice.domain.NoticeDescription;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoticeDescriptionResponse {

    private Set<String> languages;
    private String content;

    public NoticeDescriptionResponse(NoticeDescription noticeDescription) {
        this.languages = noticeDescription.getLanguages()
            .stream()
            .map(Language::getName)
            .collect(Collectors.toSet());
        this.content = noticeDescription.getContent();
    }
}
