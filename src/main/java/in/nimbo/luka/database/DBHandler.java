package in.nimbo.luka.database;

import in.nimbo.luka.ConfHandler;
import in.nimbo.luka.feed.rss.Item;
import in.nimbo.luka.utils.Constants;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBHandler {
    private Connection connection;
    private ConfHandler confHandler;

    private DBHandler() {

    }

    private static class SingletonHolder {
        private static final DBHandler INSTANCE = new DBHandler();
    }

    public static DBHandler getInstance() {
        return DBHandler.SingletonHolder.INSTANCE;
    }


    public void setup() {
        try {
            connection = openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTables(statement);
        initializeAgencies(statement);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void  initializeAgencies(Statement statement) {

        confHandler = ConfHandler.getInstance();

        File folder = new File(Constants.CONFIG_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> configFileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                configFileNames.add(listOfFiles[i].getName());
            }
        }

        for (int i = 0; i < configFileNames.size(); i++) {
            String fileName = configFileNames.get(i);
            String link = fileName.substring(0, fileName.lastIndexOf('.'));
            String query =
                    MessageFormat.format(Constants.DATABASE_INSERT_INTO_AGENCIES,
                            i, link,confHandler.getConfig(link).getBodyPattern());


            System.out.println(query);
            try {
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    private void createTables(Statement statement) {

        try {
            statement.execute(Constants.DATABASE_DROP_TABLE_IF_EXISTS_RSS_AGENCY);
            statement.execute(Constants.DATABASE_DROP_TABLE_IF_EXISTS_ITEMS);
            statement.execute(Constants.DATABASE_DROP_TABLE_IF_EXISTS_AGENCIES);


            statement.execute(Constants.DATABASE_CREATE_TABLE_AGENCIES);
            statement.execute(Constants.DATABASE_CREATE_TABLE_ITEMS);
            statement.execute(Constants.DATABASE_CREATE_TABLE_RSS_AGENCY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection openConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", Constants.DATABASE_USER);
        properties.put("password", Constants.DATABASE_PASSWORD);
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(Constants.DATABASE_URL, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection == null) {
            throw new SQLException("Unable to open connection.");
        }

        if (connection.isClosed()) {
            throw new SQLException("Connection closed.");
        }

        return connection;
    }


    public void addItem(Item item) {
    }


    public void addItems(List<Item> items) {
    }

    public void addNewsAgency() {

    }
}
