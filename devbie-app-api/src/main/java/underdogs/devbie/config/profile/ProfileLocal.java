package underdogs.devbie.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value="local")
@PropertySource({"classpath:properties/local/application.properties"})
public class ProfileLocal {
}
