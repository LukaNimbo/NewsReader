package in.nimbo.luka;
import in.nimbo.luka.database.DBHandler;
import in.nimbo.luka.utils.Constants;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

//        new NewsReader();
        testDB();
//        testRSSReader();
//        testReadConfigFiles();
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
