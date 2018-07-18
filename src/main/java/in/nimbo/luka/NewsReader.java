package in.nimbo.luka;

import in.nimbo.luka.database.DBHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsReader {

    Logger logger = LoggerFactory.getLogger(NewsReader.class);

    DBHandler dbHandler;

    public NewsReader(){
    }

    public void initializer(){
        dbHandler = DBHandler.getInstance();
        logger.info("Get instance of database handler successfully.");
        dbHandler.setup();
        logger.info("Set up database handler successfully.");
    }
}
