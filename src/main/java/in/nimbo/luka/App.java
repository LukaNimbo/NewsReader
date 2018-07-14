package in.nimbo.luka;

import in.nimbo.luka.configs.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
//        System.out.println("[START]\n");
//        RSSReader rssReader = new RSSReader();
//        String link = "http://rss.cnn.com/rss/edition_world.rss";
//        URL url = null;
//
//        try {
//            url = new URL(link);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        rssReader.read(url);
//
        ConfHandler confHandler = ConfHandler.getInstance();
//        confHandler.getConfig("www.tabnak.ir");

        Set<String> set = new HashSet<>();
        set.add(".gutter_news > div.body1");
        set.add(".gutter_news > div.body2");

        Config config = new Config(".gutter_news > div.body", set);
        confHandler.add(config, "www.alef.ir");


    }
}
