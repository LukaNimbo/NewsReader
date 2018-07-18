package in.nimbo.luka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteConfig {
    Logger logger = LoggerFactory.getLogger(SiteConfig.class);
    private String bodyPattern;
    private String link;

    public SiteConfig(String link, String bodyPattern){
        this.link = link;
        this.bodyPattern = bodyPattern;
    }

    public String getBodyPattern() {
        return bodyPattern;
    }


    public void setBodyPattern(String bodyPattern) {
        this.bodyPattern = bodyPattern;
        logger.info("Body pattern added successfully");
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
