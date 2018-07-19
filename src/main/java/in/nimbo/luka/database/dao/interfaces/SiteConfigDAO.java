package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.SiteConfig;

import java.sql.SQLException;


public interface SiteConfigDAO {

    void addConfig(SiteConfig siteConfig) throws SQLException;
    SiteConfig getConfig(String link) throws SQLException;
    void updateConfig(SiteConfig siteConfig) throws SQLException;

}
