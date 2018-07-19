package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.feed.rss.Channel;

import java.sql.SQLException;
import java.util.List;

public interface SiteChannelDAO {

    boolean existChannel(int channelId) throws SQLException;

    void addChannel(Channel channel) throws SQLException;

    Channel getChannel(int channelId) throws SQLException;

    int getChannelId(String rssLink) throws SQLException;

    List<Channel> getAllChannels() throws SQLException;

}
