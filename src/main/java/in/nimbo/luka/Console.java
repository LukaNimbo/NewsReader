package in.nimbo.luka;

import in.nimbo.luka.database.dao.implementation.RSSItemsMysqlImpl;
import in.nimbo.luka.database.dao.implementation.SiteChannelMysqlImpl;
import in.nimbo.luka.database.dao.implementation.SiteConfigMysqlIpml;
import in.nimbo.luka.database.dao.interfaces.RSSItemsDAO;
import in.nimbo.luka.database.dao.interfaces.SiteChannelDAO;
import in.nimbo.luka.database.dao.interfaces.SiteConfigDAO;
import in.nimbo.luka.feed.rss.Channel;
import in.nimbo.luka.feed.rss.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import asg.cliche.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Calendar;
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
    public void addSiteConfig(@Param(name = "link") String link, @Param(name = "bodyPattern") String bodyPattern){
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
    public String getSiteConfig(@Param(name = "link") String link){
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
    public void updateSiteConfig(@Param(name = "id") int id, @Param(name = "link") String link, @Param(name = "bodyPattern") String bodyPattern){
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
    public void getQuantityNews(@Param(name = "quantity") int quantity, @Param(name = "channelId") int channelId, @Param(name = "date{year:month:day | now}") String date){

        String[] dateInput = date.split(":");
        Date date1 = new Date();

        Calendar calendar = Calendar.getInstance();

        if (dateInput[0].equals("now")){

        }else if (dateInput.length == 3){
            calendar.set(Integer.parseInt(dateInput[0]), Integer.parseInt(dateInput[1]) - 1, Integer.parseInt(dateInput[2]));
            date1 = calendar.getTime();
            try {
                int numberOfNews = rssItemsDAO.getNewsInDay(channelId, date1).size();
                System.out.println(numberOfNews);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("Invalid input for date");
        }
    }

    @Command(description = "get latest news of each agencies")
    public void getLastestNewsOfEachAgency(@Param(name = "quantity")int quantity){
        List<Channel> channels = null;
        try {
            channels = siteChannelDAO.getAllChannels();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (Channel channel: channels){
            System.out.println(channel.toString() + "  -> ");
            List<Item> items = null;
            try {
                items = rssItemsDAO.getNews(quantity, channel.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Item item: items){
                System.out.println(item.toString());
            }
            System.out.println("\n");
        }
    }

    @Command(description = "Show channels details")
    public void showAgnecies(){
        List<SiteConfig> siteConfigs = null;
        try {
            siteConfigs = siteConfigDAO.getSiteConfigs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (siteConfigs == null){
            //TODO: show message about invalid query
        }
        if (siteConfigs.isEmpty()){
            //TODO: show message about channels is empty
            System.out.println("empty");
        }
        for (SiteConfig siteConfig: siteConfigs){
            System.out.println(siteConfig.toString());
        }

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

    @Command(description = "Get all items")
    public void showItems(){
        List<Item> items = null;
        try {
            items = rssItemsDAO.getItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Item item: items){
            System.out.println(item.toString());

        }

    }

    @Command(description = "Search text in context of news")
    public void searchInContext(@Param(name = "phrase") String phrase){
        List<Item> items = null;
        try {
            items = rssItemsDAO.searchInContext(phrase);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Item item: items){
            System.out.println(item.toString());

        }

    }

    @Command(description = "Search text in title of news")
    public void searchInTitle(@Param(name = "phrase") String phrase){

        List<Item> items = null;
        try {
            items = rssItemsDAO.searchInTitle(phrase);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (Item item: items){
            System.out.println(item.toString());

        }

    }

    @Command(description = "Exit")
    public void exit(){
        System.exit(0);
    }

}