package in.nimbo.luka;
import in.nimbo.luka.database.DBHandler;
import in.nimbo.luka.database.JDBCConnectionPool;
import in.nimbo.luka.utils.Constants;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

//        new NewsReader();
//        testDB();
//        testRSSReader();
//        testReadConfigFiles();
        testConnectionPool();
    }

    private static void testConnectionPool() {
        JDBCConnectionPool pool = new JDBCConnectionPool(
                "com.mysql.jdbc.Driver", "jdbc:mysql://localhost/test",
                "user", "pass");

        // Get a connection:
        Connection connection = pool.checkOut();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.execute(Constants.DATABASE_DROP_TABLE_IF_EXISTS_AGENCIES);
            statement.execute(Constants.DATABASE_CREATE_TABLE_AGENCIES);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void testDB() {

        System.out.println("[DATABASE-TEST] -> START");
        DBHandler dbHandler =  DBHandler.getInstance();
        dbHandler.setup();
        System.out.println("[DATABASE-TEST] -> FINISHED");

    }

    private static void testRSSReader() {
        System.out.println("[TEST-RSSParser]");

        RSSParser rssReader = new RSSParser();
        String link = "http://rss.cnn.com/rss/edition_world.rss";
        URL url = null;

        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        rssReader.parse(url, null);
    }

    private static void testReadConfigFiles() {
        File folder = new File(Constants.CONFIG_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> configFileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String filename = listOfFiles[i].getName();
                configFileNames.add(filename.substring(0, filename.lastIndexOf('.')));
            }
        }

        for (String fileName: configFileNames)
            System.out.println(fileName);
    }

}
