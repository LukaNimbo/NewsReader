package in.nimbo.luka;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigHandlerTest {
    ConfigHandler configHandler = ConfigHandler.getInstance();
    @Test
    public void getConfigTestWithTabnakConfig(){
        SiteConfig expectedConfig = new SiteConfig(".gutter_news > div.body",null);
        SiteConfig config = configHandler.getConfig("www.tabnak.ir");
        //System.out.println(expectedConfig.getBodyPattern());
        assertEquals(expectedConfig,config);
    }
}
