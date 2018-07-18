package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.feed.rss.Item;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface RSSItemsDAO {

    boolean existsItem(Item item) throws SQLException;

    void addItem(Item item) throws SQLException;

    List<Item> getLastNewsOfChannel(int numOfRows, int channelId) throws SQLException;

    int getNumberOfItemsInChannelPerDay(Date dayDate, int channelId) throws SQLException;
}
