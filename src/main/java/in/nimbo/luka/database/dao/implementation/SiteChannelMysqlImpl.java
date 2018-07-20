package in.nimbo.luka.database.dao.implementation;

import in.nimbo.luka.database.HikariConnectionPool;
import in.nimbo.luka.database.dao.interfaces.SiteChannelDAO;
import in.nimbo.luka.feed.rss.Channel;
import in.nimbo.luka.feed.rss.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteChannelMysqlImpl implements SiteChannelDAO {

    @Override
    public boolean existChannel(int channelId) throws SQLException {
        boolean exist = false;
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String existChannelQuery = "SELECT * FROM site_channel WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(existChannelQuery);
        preparedStatement.setInt(1, channelId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public void addChannel(Channel channel) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String addChannelQuery =  "INSERT INTO site_channel (title, link, description, site_config_id) VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(addChannelQuery);
        preparedStatement.setString(1, channel.getTitle());
        preparedStatement.setString(2, channel.getLink());
        preparedStatement.setString(3, channel.getDescription());
        preparedStatement.setInt(4, channel.getSiteConfigId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Channel getChannel(int channelId) throws SQLException {
        Channel channel = null;
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getChannelQuery =  "SELECT * FROM site_channel WHERE id = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(getChannelQuery);
        preparedStatement.setInt(1, channelId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            channel = new Channel();
            channel.setId(resultSet.getInt("id"));
            channel.setTitle(resultSet.getString("title"));
            channel.setLink(resultSet.getString("link"));
            channel.setDescription(resultSet.getString("description"));
            channel.setSiteConfigId(resultSet.getInt("site_config_id"));
            channel.setItems(getItems(channelId));
        }else{
            //TODO: config not found
        }
        return channel;

    }

    private List<Item> getItems(int channelId) throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getLatestNewsQuery = "SELECT rss_items.id, rss_items.title, rss_items.link, rss_items.pubDate, rss_items.description, rss_items.context, rss_items.site_channel_id " +
                "FROM site_channel, rss_items " +
                "WHERE site_channel.id = rss_items.site_channel_id and site_channel.id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getLatestNewsQuery);

        ;
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.setInt(1, channelId);

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
    public int getChannelId(String link) throws SQLException {
        int channelID = -1;
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getChannelIdQuery =  "SELECT id FROM site_channel WHERE link = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getChannelIdQuery);
        preparedStatement.setString(1, link);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            channelID = resultSet.getInt("id");
        }

        return channelID;
    }


    @Override
    public List<Channel> getAllChannels() throws SQLException {
        List<Channel> channels = new ArrayList<>();
        Connection connection= HikariConnectionPool.getInstance().getConnection();
        String getAllChannelsIdQuery =  "SELECT * FROM site_channel;";
        PreparedStatement preparedStatement = connection.prepareStatement(getAllChannelsIdQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Channel channel = new Channel();
            channel.setId(resultSet.getInt("id"));
            channel.setTitle(resultSet.getString("title"));
            channel.setLink(resultSet.getString("link"));
            channel.setDescription(resultSet.getString("description"));
            channel.setSiteConfigId(resultSet.getInt("site_config_id"));
            channel.setItems(getItems(resultSet.getInt("id")));
            channels.add(channel);
        }

        return channels;
    }
}
