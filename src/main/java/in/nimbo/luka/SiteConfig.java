package in.nimbo.luka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteConfig {
    Logger logger = LoggerFactory.getLogger(SiteConfig.class);
    private int id;
    private String bodyPattern;
    private String link;

    public SiteConfig(int id, String link, String bodyPattern){
        this.id = id;
        this.link = link;
        this.bodyPattern = bodyPattern;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBodyPattern() {
        return bodyPattern;
    }

    public void setBodyPattern(String bodyPattern) {
        this.bodyPattern = bodyPattern;
        logger.info("Body pattern added successfully");
    }
}
