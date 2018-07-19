package in.nimbo.luka;

import asg.cliche.ShellFactory;
import in.nimbo.luka.database.HikariConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class NewsReader {
    Logger logger = LoggerFactory.getLogger(NewsReader.class);
    private final String DATABASE_DATABASE_SCHEMA_FILE_PATH = "src/main/resources/schema.properties";

    public NewsReader(){

    }

    public void initialize(){
        setupDB();
        setupConsole();
    }

    private void setupConsole() {
        try {
            ShellFactory.createConsoleShell("RSS Reader", "Enter '?list' to list all commands",
                    new Console()).commandLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDB() {
        Properties setupDBTablesProperties = getSetupDBTablesProperties();
        dropTables(setupDBTablesProperties);
        createTables(setupDBTablesProperties);
    }

    private void dropTables(Properties setupDBTablesProperties) {
        Connection connection = null;
        try {
            connection = HikariConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.drop.rss-items"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.drop.site-channel"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.drop.site-config"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables(Properties setupDBTablesProperties) {

        Connection connection = null;
        try {
            connection = HikariConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.create.site-config"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.create.site-channel"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement = connection.prepareStatement(setupDBTablesProperties.getProperty("table.create.rss-items"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Properties getSetupDBTablesProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(DATABASE_DATABASE_SCHEMA_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            properties.load(inputStream);
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
        return properties;
    }
}
