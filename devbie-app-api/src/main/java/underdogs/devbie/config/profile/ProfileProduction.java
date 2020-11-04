package underdogs.devbie.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value = "production")
@PropertySource({"classpath:properties/production/application.properties"})
public class ProfileProduction {
}