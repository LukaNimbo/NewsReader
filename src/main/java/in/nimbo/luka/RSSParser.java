package in.nimbo.luka;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

import in.nimbo.luka.feed.rss.Channel;
import in.nimbo.luka.feed.rss.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSSParser {

    Logger logger = LoggerFactory.getLogger(RSSParser.class);

    private HTMLParser HTMLParser;

    public RSSParser(){
        HTMLParser = new HTMLParser();
    }

    public Channel parse(URL url, SiteConfig siteConfig) {

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new InputStreamReader(url.openStream()));
            logger.info("Get feed of rss site successfully.");
        } catch (FeedException e) {
            e.printStackTrace();
            logger.debug("Get feed of rss site has problem",e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Get feed of rss site has exception ",e);
        }
        Channel channel = new Channel();
        logger.info("Make new channel successfully.");
        channel.setName(url.getHost());
        logger.info("Set channel name successfully.");
        channel.setUrl(url);
        logger.info("Set channel url successfully.");
        channel.setDescription(feed.getDescription());
        logger.info("Set channel description successfully.");


        List<Item> items = new ArrayList<>();
        for (SyndEntry syndEntry: feed.getEntries()){
            Item item = new Item();
            item.setLink(syndEntry.getLink());
            item.setTitle(syndEntry.getTitle());
            item.setDescription(syndEntry.getDescription().getValue());
            item.setPubDate(syndEntry.getPublishedDate());

            try {
                item.setContext(HTMLParser.getContext(new URL(item.getLink()), siteConfig));
                logger.info("Item set context successfully");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                logger.debug("Set item context has exception",e);
            }

            items.add(item);
            logger.info("Item add successfully");
        }

        channel.setItems(items);
        logger.info("Set channel Items successfully.");
        return channel;
    }
}
