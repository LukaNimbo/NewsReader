package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.SiteConfig;
import java.util.List;


public interface SiteConfigDAO {

    void addConfig(SiteConfig siteConfig) throws Exception;
    SiteConfig getConfig(String link) throws Exception;
    void updateConfig(SiteConfig siteConfig) throws Exception;

    List<SiteConfig> getSiteConfigs() throws Exception;

}
