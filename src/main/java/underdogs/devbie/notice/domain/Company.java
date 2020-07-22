package underdogs.devbie.notice.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
public class Company {

    private String name;

    private Integer salary;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Company company = (Company)o;
        return Objects.equals(name, company.name) &&
            Objects.equals(salary, company.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary);
    }
}
