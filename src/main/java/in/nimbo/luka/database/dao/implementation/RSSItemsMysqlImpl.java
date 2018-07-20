package in.nimbo.luka.database.dao.implementation;

import in.nimbo.luka.database.HikariConnectionPool;
import in.nimbo.luka.database.dao.interfaces.RSSItemsDAO;
import in.nimbo.luka.feed.rss.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RSSItemsMysqlImpl implements RSSItemsDAO {

    @Override
    public boolean exitsItemById(int itemId) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String existItemQuery = "SELECT * FROM rss_items WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(existItemQuery);
        preparedStatement.setInt(1, itemId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean exitsItemByLink(String link) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String existItemQuery = "SELECT * FROM rss_items WHERE link = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(existItemQuery);
        preparedStatement.setString(1, link);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public void addItem(Item item) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String existItemQuery = "INSERT INTO rss_items (title, link, pubDate, description, context, site_channel_id) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(existItemQuery);
        preparedStatement.setString(1, item.getTitle());
        preparedStatement.setString(2, item.getLink());
        preparedStatement.setTimestamp(3, new Timestamp(item.getPubDate().getTime()));
        preparedStatement.setString(4, item.getDescription());
        preparedStatement.setString(5, item.getContext());
        preparedStatement.setInt(6, item.getChannelId());
        preparedStatement.executeUpdate();
    }


    @Override
    public Item getItemById(int itemId) throws SQLException {
        Item item = new Item();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String existItemQuery = "SELECT * FROM rss_items WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(existItemQuery);
        preparedStatement.setInt(1, itemId);
        ResultSet resultSet = preparedStatement.executeQuery();
        item.setId(resultSet.getInt("id"));
        item.setTitle(resultSet.getString("title"));
        item.setLink(resultSet.getString("link"));
        item.setPubDate(resultSet.getTimestamp("pubDate"));
        item.setDescription(resultSet.getString("description"));
        item.setContext(resultSet.getString("context"));
        item.setChannelId(resultSet.getInt("site_channel_id"));

        return item;
    }

    @Override
    public List<Item> getItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String searchInTitleQuery =  "SELECT * FROM rss_items;";
        PreparedStatement preparedStatement = connection.prepareStatement(searchInTitleQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;
    }

    @Override
    public List<Item> getNews(int quantity, int channelId, Date date) throws SQLException {


        //TODO: inja bayad megah konam
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getLatestNewsQuery = "SELECT rss_items.id, rss_items.title, rss_items.link, rss_items.pubDate, rss_items.description, rss_items.context, rss_items.site_channel_id " +
                "FROM site_channel, rss_items " +
                "WHERE site_channel.id = rss_items.site_channel_id and site_channel.id = ? and rss_items.pubDate = ? " +
                "LIMIT ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getLatestNewsQuery);

        ;
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.setInt(1, channelId);
        preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
        preparedStatement.setInt(3, quantity);

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;
    }

    @Override
    public List<Item> getNews(int quantity, int channelId) throws SQLException {

        //TODO: inja bayad megah konam
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getLatestNewsQuery = "SELECT rss_items.id, rss_items.title, rss_items.link, rss_items.pubDate, rss_items.description, rss_items.context, rss_items.site_channel_id " +
                "FROM site_channel, rss_items " +
                "WHERE site_channel.id = rss_items.site_channel_id and site_channel.id = ? " +
                "ORDER BY rss_items.pubDate LIMIT ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getLatestNewsQuery);

        ;
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.setInt(1, channelId);
        preparedStatement.setInt(2, quantity);

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;
    }

    @Override
    public List<Item> getNewsInDay(int channelId, Date date) throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getNewsInDayQuery =  "SELECT * FROM rss_items WHERE site_channel_id = ? AND pubDate BETWEEN ? AND ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(getNewsInDayQuery);
        Date startDate = startDate(date);
        Date endDate = endDate(date);
        preparedStatement.setInt(1, channelId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;
    }

    @Override
    public List<Item> searchInContext(String phrase) throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String searchInTitleQuery =  "SELECT * FROM rss_items WHERE context LIKE ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(searchInTitleQuery);
        preparedStatement.setString(1, "'%" + phrase + "%'");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;

    }

    @Override
    public List<Item> searchInTitle(String phrase) throws SQLException {

        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String searchInTitleQuery =  "SELECT * FROM rss_items WHERE title LIKE ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(searchInTitleQuery);
        preparedStatement.setString(1, "'%" + phrase + "%'");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Item item = new Item();
            item.setId(resultSet.getInt("id"));
            item.setTitle(resultSet.getString("title"));
            item.setLink(resultSet.getString("link"));
            item.setPubDate(resultSet.getTimestamp("pubDate"));
            item.setDescription(resultSet.getString("description"));
            item.setContext(resultSet.getString("context"));
            item.setChannelId(resultSet.getInt("site_channel_id"));
            items.add(item);
        }

        return items;

    }
//    SELECT *
//    FROM site_config, site_channel, rss_items
//    WHERE site_channel.id = site_channel.site_config_id and site_channel.id = rss_items.site_channel_id
//    ORDER BY rss_items.pubDate DESC LIMIT 10;
//
//    List<String> latestNewsOf = new ArrayList<>();
//    Connection connection = HikariConnectionPool.getInstance().getConnection();
//    String searchInTitleQuery =  "SELECT *\n" +
//            "FROM site_config, site_channel, rss_items\n" +
//            "WHERE site_channel.id = site_channel.site_config_id and site_channel.id = rss_items.site_channel_id\n" +
//            "ORDER BY rss_items.pubDate DESC LIMIT 10;";
//    PreparedStatement preparedStatement = connection.prepareStatement(searchInTitleQuery);
//        preparedStatement.setString(1, "'%" + phrase + "%'");
//    ResultSet resultSet = preparedStatement.executeQuery();
    @Override
    public List<String> getLatestNewsOfAgencies() throws SQLException {
        return null;

    }

    private Date startDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private Date endDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
