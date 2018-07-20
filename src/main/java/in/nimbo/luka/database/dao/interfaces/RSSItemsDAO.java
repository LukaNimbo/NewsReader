package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.feed.rss.Item;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface RSSItemsDAO {

    boolean exitsItemById(int itemId) throws SQLException;

    boolean exitsItemByLink(String link) throws SQLException;

    void addItem(Item item) throws SQLException;

    Item getItemById(int itemId) throws SQLException;

    List<Item> getItems() throws SQLException;

    List<Item> getNews(int quantity, int channelId) throws SQLException;

    List<Item> getNews(int quantity, int channelId, Date date) throws SQLException;


    List<Item> getNewsInDay(int channelId, Date startDate) throws SQLException;

    List<Item> searchInContext(String phrase) throws SQLException;

    List<Item> searchInTitle(String phrase) throws SQLException;

    List<String> getLatestNewsOfAgencies() throws SQLException;

}
