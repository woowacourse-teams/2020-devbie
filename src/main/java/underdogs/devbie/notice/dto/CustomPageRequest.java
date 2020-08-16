package underdogs.devbie.notice.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public final class CustomPageRequest {

    private static final int MAX_SIZE = 50;
    private static final int DEFAULT_SIZE = 10;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    private Sort.Direction direction;

    public void setPage(Integer page) {
        this.page = Math.max(page, 1);
    }

    public void setSize(Integer size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public PageRequest toPageRequest() {
        if (Objects.isNull(direction)) {
            this.direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page - 1, size, Sort.by(direction, "createdDate"));
    }
}
