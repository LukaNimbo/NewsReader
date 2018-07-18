package in.nimbo.luka.database.dao.implementation;

import in.nimbo.luka.SiteConfig;
import in.nimbo.luka.database.HikariConnectionPool;
import in.nimbo.luka.database.dao.interfaces.SiteConfigDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SiteConfigMysqlIpml implements SiteConfigDAO {

    @Override
    public void addConfig(SiteConfig siteConfig) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String addConfigQuery =  "INSERT INTO site_config (link, bodyPattern) VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(addConfigQuery);
        preparedStatement.setString(1, siteConfig.getLink());
        preparedStatement.setString(2, siteConfig.getBodyPattern());
        preparedStatement.executeUpdate();
    }

    @Override
    public SiteConfig getConfig(String link) throws SQLException {
        SiteConfig siteConfig = null;
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String getConfigQuery =  "SELECT * FROM site_config WHERE link = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getConfigQuery);
        preparedStatement.setString(1, link);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
           siteConfig = new SiteConfig(resultSet.getInt("id"), resultSet.getString("link"), resultSet.getString("bodyPattern"));
        }else{
            //TODO: config not found
        }
        return siteConfig;
    }


    @Override
    public void updateConfig(SiteConfig siteConfig) throws SQLException {
        Connection connection = HikariConnectionPool.getInstance().getConnection();
        String updateConfigQuery =  "UPDATE site_config SET bodyPattern = ? WHERE id = ? OR link = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updateConfigQuery);
        preparedStatement.setString(1, siteConfig.getBodyPattern());
        preparedStatement.setInt(2, siteConfig.getId());
        preparedStatement.setString(3, siteConfig.getLink());
        preparedStatement.executeUpdate();
    }
}
