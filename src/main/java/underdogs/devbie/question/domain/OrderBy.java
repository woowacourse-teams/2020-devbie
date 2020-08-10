package underdogs.devbie.question.domain;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;

@Getter
public enum OrderBy {
    CREATED_DATE("createdDate", Direction.ASC),
    VISITS("visits", Direction.DESC);

    private final String propertyName;
    private final Direction direction;

    OrderBy(String propertyName, Direction direction) {
        this.propertyName = propertyName;
        this.direction = direction;
    }
}
