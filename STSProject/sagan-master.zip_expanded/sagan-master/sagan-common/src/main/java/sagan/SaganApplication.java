package sagan;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static sagan.SaganProfiles.*;

/**
 * A specialized {@link SpringApplication} that enforces {@link SaganProfiles} semantics.
 * See sagan.SiteApplication and sagan.IndexerApplication classes for usage examples.
 */
public class SaganApplication extends SpringApplication {

    private static final Log logger = LogFactory.getLog(SaganApplication.class);

    public SaganApplication(Class<?> configClass) {
        super(configClass);
    }

    /**
     * Enforce mutual exclusivity and implicit activation of profiles as described in
     * {@link SaganProfiles}.
     */
    @Override
    protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
        super.configureProfiles(environment, args);

        boolean stagingActive = environment.acceptsProfiles(STAGING);
        boolean productionActive = environment.acceptsProfiles(PRODUCTION);

        if (stagingActive && productionActive) {
            throw new IllegalStateException(format("Only one of the following profiles may be specified: [%s]",
                    arrayToCommaDelimitedString(new String[] { STAGING, PRODUCTION })));
        }

        if (stagingActive || productionActive) {
            logger.info(format("Activating '%s' profile because one of '%s' or '%s' profiles have been specified.",
                    CLOUDFOUNDRY, STAGING, PRODUCTION));
            environment.addActiveProfile(CLOUDFOUNDRY);
        }
        else {
            logger.info("The default 'standalone' profile is active because no other profiles have been specified.");
            environment.addActiveProfile(STANDALONE);
        }

    }
}
