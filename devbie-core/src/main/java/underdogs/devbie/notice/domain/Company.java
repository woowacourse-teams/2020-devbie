package underdogs.devbie.notice.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import underdogs.devbie.exception.CreateFailException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Company {

    private static final int SALARY_MINIMUM_VALUE = 0;

    private String name;

    public Company(String name) {
        validateParameters(name);
        this.name = name;
    }

    private void validateParameters(String name) {
        if (checkEmpty(name)) {
            throw new CreateFailException();
        }
    }

    private boolean checkEmpty(String name) {
        return Objects.isNull(name) || name.trim().isEmpty();
    }
}
