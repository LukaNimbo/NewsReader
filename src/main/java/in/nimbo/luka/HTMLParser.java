package in.nimbo.luka;

import java.io.IOException;
import java.net.URL;

/**
 * we used Jsoup library that give this possibility
 * to us to get html file of a site and getContext that
 * with given css query selector
 *
 * @author nadi
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTMLParser Class, get a URL and a SiteConfig and then
 * get the HTML file of that url link and return the
 * context of that link in output.
 *
 * @author nadi
 */

public class HTMLParser {

    Logger logger = LoggerFactory.getLogger(HTMLParser.class);


    public HTMLParser(){

    }

    /**
     *
     * @param url is link of one news of News Site that we want to
     *            get the context of that.
     * @param bodyPattern is unique file that defines where we search the
     *               context of news in the html file of site.
     * @return context of a news of site
     *
     * @author nadi
     */

    public String getContext(URL url, String bodyPattern) {
        Document document = null;
        try {
            document = Jsoup.connect(url.toString()).get();
            logger.info("Jsoup connect to site and read HTML successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Jsoup can't read HTML of site",e);
        }

        Elements newsPara = document.select(bodyPattern);

        //String context = "";
        StringBuilder context = new StringBuilder("");

        for (Element newsPar : newsPara) {
            context.append (newsPar.text()+"\n");
        }

        return context.toString();
    }
}