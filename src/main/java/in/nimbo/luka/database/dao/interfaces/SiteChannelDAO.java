package in.nimbo.luka.database.dao.interfaces;

import in.nimbo.luka.feed.rss.Channel;

import java.util.List;

public interface SiteChannelDAO {

    boolean existChannel(int channelId) throws Exception;

    void addChannel(Channel channel) throws Exception;

    Channel getChannel(int channelId) throws Exception;

    int getChannelId(String rssLink) throws Exception;

    List<Channel> getAllChannels() throws Exception;

}
