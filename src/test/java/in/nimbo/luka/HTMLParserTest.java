package in.nimbo.luka;

import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import static org.junit.Assert.*;

public class HTMLParserTest {
    @Test
    public void testHTMLParserWithTabnakSite () throws IOException {
        HTMLParser htmlParse = new HTMLParser();
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("./HTMLParserTest.txt"));
        URL url = new URL(properties.get("TABNAK_NEWS_URL").toString());
        SiteConfig config = new SiteConfig(".gutter_news > div.body",null);
        String context = htmlParse.parse(url,config);
        System.out.println(properties.get("TABNAK_NEWS_CONTEXT").toString());
        //assertEquals(properties.get("TABNAK_NEWS_CONTEXT").toString(), context);
        assertTrue(context.contains("مذاکره با آمریکا"));
    }
}