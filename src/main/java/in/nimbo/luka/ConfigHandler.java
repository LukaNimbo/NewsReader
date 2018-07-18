package in.nimbo.luka;

import in.nimbo.luka.utils.Constants;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ConfigHandler {
    private ConfigHandler(){}

    private static class SingletonHolder {
        private static final ConfigHandler INSTANCE = new ConfigHandler();
    }

    public static ConfigHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Extract configuration of website using the name of site,
     * Used properties class to get entries of configuration.
     * @param  site the name of website.
     * @return      the config at the specified website.
     */
    public SiteConfig getConfig(String site){
        String path = Constants.CONFIG_DIRECTORY;
        String fileName = site.concat(Constants.CONFIG_EXTENSION);
        SiteConfig config;

        File directory = new File(path);
        if (!directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }

            if (file.getName().equals(fileName)) {
                Properties confProp = new Properties();

                try {
                    confProp.load(new FileInputStream(file.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String bodyPattern = "";
                Set<String> adPatterns = new HashSet<>();

                for (String property: confProp.stringPropertyNames()){
                    if(property.contains(Constants.CONFIG_BODY_PATTERN_KEYWORD)){
                        bodyPattern = confProp.getProperty(property);
                    }else{
                        adPatterns.add(confProp.getProperty(property));
                    }
                }

//                System.out.println(bodyPattern);
//                for (String s: adPatterns){
//                    System.out.println(s);
//                }


                //TODO: id set 0 incorrect.
                config = new SiteConfig(0, "", bodyPattern);
                return config;
            }
        }

        return null;
    }







    public void addConfig(SiteConfig siteConfig) {

        String fileName = siteConfig.getLink().concat(Constants.CONFIG_EXTENSION);
        String filePath = Constants.CONFIG_DIRECTORY.concat(fileName);

        Properties confProp = new Properties();
        confProp.setProperty(Constants.CONFIG_BODY_PATTERN_KEYWORD, siteConfig.getBodyPattern());

        int adIndex = 1;
//        for (String adPattern: config.getAdPatterns()){
//            String property = Constants.CONFIG_AD_PATTERNS_KEYWORD.concat(Integer.toString(adIndex));
//            confProp.setProperty(property, adPattern);
//            adIndex++;
//        }

        File file = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            confProp.store(fileOutputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

