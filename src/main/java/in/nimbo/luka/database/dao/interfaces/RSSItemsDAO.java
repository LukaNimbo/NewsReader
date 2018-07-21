package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.feed.rss.Item;

import java.util.Date;
import java.util.List;

public interface RSSItemsDAO {

    boolean exitsItemById(int itemId) throws Exception;

    boolean exitsItemByLink(String link) throws Exception;

    void addItem(Item item) throws Exception;

    Item getItemById(int itemId) throws Exception;

    List<Item> getItems() throws Exception;

    List<Item> getNews(int quantity, int channelId) throws Exception;

    List<Item> getNews(int quantity, int channelId, Date date) throws Exception;


    List<Item> getNewsInDay(int channelId, Date startDate) throws Exception;

    List<Item> searchInContext(String phrase) throws Exception;

    List<Item> searchInTitle(String phrase) throws Exception;

    List<String> getLatestNewsOfAgencies() throws Exception;

}
