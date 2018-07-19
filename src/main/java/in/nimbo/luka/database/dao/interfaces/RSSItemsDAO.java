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

    List<Item> getNews(int quantity, int channelId, Date date) throws SQLException;


}
