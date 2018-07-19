package in.nimbo.luka;

import in.nimbo.luka.database.dao.implementation.SiteConfigMysqlIpml;
import in.nimbo.luka.database.dao.interfaces.SiteConfigDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.*;

import java.sql.SQLException;

public class Console {

    private SiteConfigDAO siteConfigDAO;
    private final static Logger logger = LoggerFactory.getLogger(Console.class);
    public Console(){
        siteConfigDAO = new SiteConfigMysqlIpml();
    }

    @Command(description = "Tell how extract news context by bodyPattern of site")
    public void addConfig(@Param(name = "link") String link, @Param(name = "bodyPattern") String bodyPattern){
        SiteConfig siteConfig = new SiteConfig(-1, link, bodyPattern);

        try {
            siteConfigDAO.addConfig(siteConfig);
            logger.info("addConfig of Console.java work successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("SQL Exception in addConfig of Console.java", e);
        }
    }

    @Command(description = "Get config of site")
    public String getConfig(@Param(name = "link") String link){
        SiteConfig siteConfig = null;
        try {
            siteConfig = siteConfigDAO.getConfig(link);
            logger.info("getConfig of Console.java work successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("SQL Exception in getConfig of Console.java", e);
        }
        if (siteConfig != null) {
            return siteConfig.getBodyPattern();
        }
        else {
            logger.trace("User get null Config for link: " + link);
            return "There is no Config for this link";
        }
    }

    @Command(description = "Update bodyPattern of site with id")
    public void updateConfig(@Param(name = "id") int id, @Param(name = "link") String link, @Param(name = "bodyPattern") String bodyPattern){
        SiteConfig siteConfig = new SiteConfig(id, link, bodyPattern);
        try {
            siteConfigDAO.updateConfig(siteConfig);
            logger.info("updtaeConfig of Console.java work successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("SQL Exception in updateConfig of Console.java",e);
        }
    }
}