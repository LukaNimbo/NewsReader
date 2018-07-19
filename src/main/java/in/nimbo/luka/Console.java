package in.nimbo.luka;

import in.nimbo.luka.database.dao.implementation.RSSItemsMysqlImpl;
import in.nimbo.luka.database.dao.implementation.SiteChannelMysqlImpl;
import in.nimbo.luka.database.dao.implementation.SiteConfigMysqlIpml;
import in.nimbo.luka.database.dao.interfaces.RSSItemsDAO;
import in.nimbo.luka.database.dao.interfaces.SiteChannelDAO;
import in.nimbo.luka.database.dao.interfaces.SiteConfigDAO;
import in.nimbo.luka.feed.rss.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Console {

    private SiteConfigDAO siteConfigDAO;
    private SiteChannelDAO siteChannelDAO;
    private RSSItemsDAO rssItemsDAO;
    private final static Logger logger = LoggerFactory.getLogger(Console.class);
    public Console(){
        this.siteConfigDAO = new SiteConfigMysqlIpml();
        this.siteChannelDAO = new SiteChannelMysqlImpl();
        this.rssItemsDAO = new RSSItemsMysqlImpl();

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
            logger.info("updateConfig of Console.java work successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("SQL Exception in updateConfig of Console.java",e);
        }
    }

    @Command(description = "Latest news of site")
    public void getNews(@Param(name = "quantity") int quantity,
                        @Param(name = "channelId") int channelId,
                        @Param(name = "date{year:month:day | now}") String date){



    }

    public void getQuantityNews(@Param(name = "channelId") int channelId, @Param(name = "date{year:month:day | now}") String da){


    }

    @Command(description = "Show channels details")
    public void showChannels(){
        List<Channel> channels = null;
        try {
            channels = siteChannelDAO.getAllChannels();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (channels == null){
            //TODO: show message about invalid query
        }
        if (channels.isEmpty()){
            //TODO: show message about channels is empty
            System.out.println("empty");
        }
        for (Channel channel: channels){
            System.out.println(channel.toString());
        }
    }

    public void showItems(){
    }

    @Command(description = "Search text in title and context of news")
    public void search(){

    }

    public void exit(@Param(name = "exit") String exit){
        System.exit(0);
    }



}