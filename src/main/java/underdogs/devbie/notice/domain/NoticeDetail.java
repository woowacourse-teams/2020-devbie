package underdogs.devbie.notice.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Lob;

public class NoticeDetail {

    private List<String> languages;

    private String jobPosition;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String description;
}
