package in.nimbo.luka;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigHandlerTest {
    ConfigHandler configHandler = ConfigHandler.getInstance();
    @Test
    public void getConfigTestWithTabnakConfig(){
        Config expectedConfig = new Config(".gutter_news > div.body",null);
        Config config = configHandler.getConfig("www.tabnak.ir");
        //System.out.println(expectedConfig.getBodyPattern());
        assertEquals(expectedConfig,config);
    }
}
