package live.midreamsheep.optb.config;

import live.midreamsheep.optb.function.SIO;

import java.util.Arrays;

public class ConfigTool {

    public void readConfig(String configFile) {
        Arrays.stream(SIO.inputString(configFile).split("\n"))
                .filter(s -> !s.trim().startsWith("#")&&!s.trim().isEmpty())
                .forEach(s->injectData(s.trim()));
    }
    public void injectData(String oneConfig) {
        String[] split = oneConfig.split("=");
        if(split.length!=2){
            return;
        }
        ConfigInjector.getConfig(split[0].trim()).injectData(split[1].trim());
    }
}
