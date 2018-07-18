package in.nimbo.luka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SiteConfig {
    Logger logger = LoggerFactory.getLogger(SiteConfig.class);
    private String bodyPattern;
    private Set<String> adPatterns;
    private String link;

    public SiteConfig(String bodyPattern, Set<String> adPatterns){
        this.bodyPattern = bodyPattern;
        this.adPatterns = adPatterns;
    }

    public String getBodyPattern() {
        return bodyPattern;
    }

    public Set<String> getAdPatterns() {
        return adPatterns;
    }

    public void setBodyPattern(String bodyPattern) {
        this.bodyPattern = bodyPattern;
        logger.info("Body pattern added successfully");
    }


}
