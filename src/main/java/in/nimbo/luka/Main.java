package in.nimbo.luka;

import asg.cliche.*;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException {
        NewsReader newsReader = new NewsReader();
        newsReader.initialize();
        ShellFactory.createConsoleShell("Hello","NewsReader",new Console()).commandLoop();
    }
}
