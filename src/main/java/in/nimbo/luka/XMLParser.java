package in.nimbo.luka;

import in.nimbo.luka.Config;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * XMLParser Class, get a URL and a Config and then
 * get the HTML file of that url link and return the
 * context of that link in output.
 *
 * @author nadi
 */

public class XMLParser {

    public XMLParser(){

    }

    public String parse(URL url, Config config) {
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