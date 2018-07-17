package in.nimbo.luka;

import java.io.IOException;
import java.net.URL;

/**
 * we used Jsoup library that give this possibility
 * to us to get html file of a site and parse that
 * with given css query selector
 *
 * @author nadi
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * HTMLParser Class, get a URL and a SiteConfig and then
 * get the HTML file of that url link and return the
 * context of that link in output.
 *
 * @author nadi
 */

public class HTMLParser {

    public HTMLParser(){

    }

    /**
     *
     * @param url is link of one news of News Site that we want to
     *            get the context of that.
     * @param config is unique file that defines where we search the
     *               context of news in the html file of site.
     * @return context of a news of site
     *
     * @author nadi
     */

    public String parse(URL url, SiteConfig config) {
        Document document = null;
        try {
            document = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements newsPara = document.select(config.getBodyPattern());

        String context = "";

        for (Element newsPar : newsPara) {
            context += (newsPar.text()+"\n");
        }

        return context;
    }
}