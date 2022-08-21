package live.midreamsheep.optb.config;

import live.midreamsheep.optb.config.configs.ConfigInter;
import live.midreamsheep.optb.data.SocketConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigInjector {

    public static final Map<String, ConfigInter> ConfigMap = new HashMap<>();
    static{
        ConfigMap.put("ip",data-> SocketConfig.IP = data.equals("")?"127.0.0.1":data);
        ConfigMap.put("port",data-> SocketConfig.PORT = Integer.parseInt(data.equals("")?"7751":data));
        ConfigMap.put("downloadFile",data-> SocketConfig.DOWNLOAD_FILE = new File(data.equals("")?"E://download":data));
    }
    public static ConfigInter getConfig(String configName){
        return Optional.ofNullable(ConfigMap.get(configName)).orElse(data->{});
    }
}
