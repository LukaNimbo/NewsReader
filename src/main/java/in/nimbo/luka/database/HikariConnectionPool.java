package in.nimbo.luka.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionPool {
    private volatile HikariDataSource hikariDataSource;
    private final String DATABASE_DATABASE_CONFIG_FILE_PATH = "src/main/resources/dbConfig.properties";

    private HikariConnectionPool(){
        setup();
    }

    private static class SingletonHolder {
        private static final HikariConnectionPool INSTANCE = new HikariConnectionPool();
    }

    public static HikariConnectionPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void setup(){

        Properties dbProperties = getDBProperties();
        if (dbProperties.isEmpty()){
            //TODO: dpProperties is empty, must be log.
        }

        String jdbcURLTemplate = dbProperties.getProperty("jdbcURLTemplate");
        String dbUsername = dbProperties.getProperty("username");
        String dbPassword= dbProperties.getProperty("password");
        String dbHostname = dbProperties.getProperty("hostname");
        String dbDatabaseName= dbProperties.getProperty("databaseName");
        String jdbcURL = String.format(jdbcURLTemplate, dbHostname, dbDatabaseName);

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcURL);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(config);

    }

    private Properties getDBProperties() {
        Properties dbProperties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(DATABASE_DATABASE_CONFIG_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dbProperties;
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public synchronized static void close() {
    }

}
