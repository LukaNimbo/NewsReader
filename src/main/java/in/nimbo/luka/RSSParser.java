package in.nimbo.luka;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

import in.nimbo.luka.feed.rss.Channel;
import in.nimbo.luka.feed.rss.Item;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSSParser {
    private HTMLParser HTMLParser;

    public RSSParser(){
        HTMLParser = new HTMLParser();
    }

    public Channel parse(URL url, SiteConfig config) {

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new InputStreamReader(url.openStream()));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Channel channel = new Channel();

        channel.setName(url.getHost());
        channel.setUrl(url);
        channel.setDescription(feed.getDescription());


        List<Item> items = new ArrayList<>();
        for (SyndEntry syndEntry: feed.getEntries()){
            Item item = new Item();
            item.setLink(syndEntry.getLink());
            item.setTitle(syndEntry.getTitle());
            item.setDescription(syndEntry.getDescription().getValue());
            item.setPubDate(syndEntry.getPublishedDate());

            try {
                item.setContext(HTMLParser.parse(new URL(item.getLink()), config));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            items.add(item);
        }

        channel.setItems(items);

        return channel;
    }
}
