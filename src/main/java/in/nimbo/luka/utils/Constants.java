package in.nimbo.luka.utils;

public final class Constants {
    public static final String CONFIG_DIRECTORY = "src/main/java/in/nimbo/luka/configs/";
    public static final String CONFIG_EXTENSION = ".properties";


    public static final String CONFIG_BODY_PATTERN_KEYWORD = "bodyPattern";
    public static final String CONFIG_AD_PATTERNS_KEYWORD = "adPattern";

    /**
     * Database section
     */

    /**
     * Database setup section.
     */
    public static final String DATABASE_URL = "jdbc:mysql://localhost/NewsReader";
    public static final String DATABASE_USER = "user";
    public static final String DATABASE_PASSWORD = "pass";



    /**
     * Database table names.
     */
//    public static final String DATABASE_TABLE_NEWS = "News";
//    public static final String DATABASE_DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS {0};";
//    public static final String DATABASE_CREATE_TABLE_NEWS = "CREATE TABLE {0};";



    public static final String DATABASE_DROP_TABLE_IF_EXISTS_AGENCIES = "DROP TABLE IF EXISTS agencies;";
    public static final String DATABASE_DROP_TABLE_IF_EXISTS_ITEMS = "DROP TABLE IF EXISTS items;";
    public static final String DATABASE_DROP_TABLE_IF_EXISTS_RSS_AGENCY = "DROP TABLE IF EXISTS rssAgency;";


    public static final String DATABASE_CREATE_TABLE_AGENCIES = "CREATE TABLE agencies(\n" +
            "    id int PRIMARY KEY, \n" +
            "    link VARCHAR(3000),\n" +
            "    bodyConfig VARCHAR(500)\n" +
            ");";


    public static final String DATABASE_CREATE_TABLE_ITEMS = "CREATE TABLE items(\n" +
            "    id int,\n" +
            "    title VARCHAR(100),\n" +
            "    link VARCHAR(3000),\n" +
            "    pubDate TIMESTAMP,\n" +
            "    description text,\n" +
            "    context text,\n" +
            "    agency_id int,\n" +
            "    \n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (agency_id) REFERENCES agencies(id)\n" +
            ");";


    public static final String DATABASE_CREATE_TABLE_RSS_AGENCY = "CREATE TABLE rssAgency(\n" +
            "    id int,\n" +
            "    link VARCHAR(3000),\n" +
            "    agency_id int,\n" +
            "    PRIMARY KEY (id, agency_id),\n" +
            "    FOREIGN KEY (agency_id) REFERENCES agencies(id)\n" +
            ");";

    /**
     * Database Queries.
     *
     */


    public static final String DATABASE_INSERT_INTO_AGENCIES = "INSERT INTO agencies (id, link, bodyConfig) VALUES ({0}, \"{1}\", \"{2}\");";






}
