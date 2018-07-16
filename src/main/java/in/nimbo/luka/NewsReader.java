package in.nimbo.luka;

import in.nimbo.luka.database.DBHandler;

public class NewsReader {

    DBHandler dbHandler;

    public NewsReader(){
    }

    public void initializer(){
        dbHandler = DBHandler.getInstance();
        dbHandler.setup();
    }
}
