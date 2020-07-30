package underdogs.devbie.notice.dto;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.notice.domain.JobPosition;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class NoticeResponse {

    private Long id;
    private String name;
    private Set<String> languages;
    private JobPosition jobPosition;
    private String image;
}
