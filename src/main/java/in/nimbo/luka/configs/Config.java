package in.nimbo.luka.configs;


import java.util.Set;

public class Config {
    private String bodyPattern;
    private Set<String> adPatterns;
    public Config(String bodyPattern, Set<String> adPatterns){
        this.bodyPattern = bodyPattern;
        this.adPatterns = adPatterns;
    }

    public String getBodyPattern() {
        return bodyPattern;
    }

    public Set<String> getAdPatterns() {
        return adPatterns;
    }

    public void setBodyPattern(String bodyPattern) {
        this.bodyPattern = bodyPattern;
    }


    public void setAdPatterns(Set<String> adPatterns) {
        this.adPatterns = adPatterns;
    }

    public void addAdPattern(String adPattern){
        this.adPatterns.add(adPattern);
    }

    public void removeAdPattern(String adPattern){
        if (adPattern.contains(adPattern)){
            adPatterns.remove(adPattern);
        }
    }
}
