package underdogs.devbie.notice.domain;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class NoticeDetail {

    @Embedded
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "notice_id"), name = "language")
    private Set<String> languages;

    private String jobPosition;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String description;
}
