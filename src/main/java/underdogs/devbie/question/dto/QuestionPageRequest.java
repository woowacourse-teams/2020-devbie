package underdogs.devbie.question.dto;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.question.domain.OrderBy;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public final class QuestionPageRequest {

    private static final int FIRST_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    private Integer page;
    private OrderBy orderBy;

    public void setPage(Integer page) {
        this.page = Math.max(page, 1);
    }

    public PageRequest toPageRequest() {
        setDefaultValue();
        return PageRequest.of(page - 1, DEFAULT_SIZE, Sort.by(orderBy.getDirection(), orderBy.getPropertyName()));
    }

    private void setDefaultValue() {
        if (Objects.isNull(page)) {
            this.page = FIRST_PAGE;
        }
    }
}
