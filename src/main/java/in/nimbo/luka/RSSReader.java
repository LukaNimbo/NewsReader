package in.nimbo.luka;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class RSSReader {

    public SyndFeed read(URL url) {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new InputStreamReader(url.openStream()));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;
    }

}
