package underdogs.devbie.notice.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NoticeCreateRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String name;
    private String salary;
    private List<String> languages;

    private String jobPosition;
    private String image;
}
