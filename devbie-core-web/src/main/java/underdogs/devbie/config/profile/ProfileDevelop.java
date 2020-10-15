package underdogs.devbie.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value="develop")
@PropertySource({"classpath:properties/develop/application.properties"})
public class ProfileDevelop {
}
