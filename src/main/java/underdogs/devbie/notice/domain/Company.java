package underdogs.devbie.notice.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import underdogs.devbie.notice.expception.CreateFailException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Company {

    private static final int SALARY_MINIMUM_VALUE = 0;

    private String name;

    private Integer salary;

    public Company(String name, int salary) {
        validateParameters(name, salary);
        this.name = name;
        this.salary = salary;
    }

    private void validateParameters(String name, int salary) {
        if (checkEmpty(name)) {
            throw new CreateFailException();
        }
        if (salary <= SALARY_MINIMUM_VALUE) {
            throw new CreateFailException();
        }
    }

    private boolean checkEmpty(String name) {
        return Objects.isNull(name) || name.trim().isEmpty();
    }
}
