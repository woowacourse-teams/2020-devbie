package underdogs.devbie.notice.domain;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import exception.CreateFailException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class ApplyUrl {

    private String applyUrl;

    public ApplyUrl(String url) {
        validate(url);
        this.applyUrl = url;
    }

    private void validate(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new CreateFailException();
        }
    }
}
