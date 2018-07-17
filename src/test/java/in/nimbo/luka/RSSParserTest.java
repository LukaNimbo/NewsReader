package in.nimbo.luka;

import in.nimbo.luka.feed.rss.Channel;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class RSSParserTest {
    @Test
    public void testRSSParserWithTabnakSite () throws IOException {
        RSSParser rssParser = new RSSParser();
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream(""));
        URL url = new URL(properties.get("TABNAK_NEWS_URL").toString());
        SiteConfig config = new SiteConfig(".gutter_news > div.body",null);
        Channel expected = new Channel();
        Channel output = rssParser.parse(url,config);
        assertEquals (expected,output);
    }
}
