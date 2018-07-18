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

    private final String DATABASE_JDBC_URL = "jdbc:mysql://localhost/NewsReader";
    private final String DATABASE_USERNAME = "reza";
    private final String DATABASE_PASSWORD = "root";
    private volatile HikariDataSource hikariDataSource;


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

//        Properties dbProperties = getDBProperties();
//        if (dbProperties.isEmpty()){
//            //TODO: dpProperties is empty, must be log.
//        }
        System.out.println("[SETUP HikariConnectionPool]");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DATABASE_JDBC_URL);
        config.setUsername(DATABASE_USERNAME);
        config.setPassword(DATABASE_PASSWORD);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(config);

    }

    private Properties getDBProperties() {
        Properties dbProperties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/dbConfig.properties");
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
