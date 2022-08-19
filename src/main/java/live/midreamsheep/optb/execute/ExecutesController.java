package live.midreamsheep.optb.execute;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.back.BackHandler;
import live.midreamsheep.optb.execute.monitor.ComputerMonitor;
import live.midreamsheep.optb.execute.tool.Translation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ExecutesController {
    private static final Map<String,ExecuteHandlerInter> executeHandlers = new HashMap<>();

    public static boolean isRunning = false;

    static{
        //翻译功能 ctrl+shift+x
        executeHandlers.put("294245",new Translation());
        //回退到主页面 ctrl+b
        executeHandlers.put("2948",new BackHandler());
        //启动监听功能 ctrl+m
        executeHandlers.put("2950",new ComputerMonitor());
    }

    public static ExecuteHandlerInter getExecuteHandlers(String keyCodeHash) {
        ExecuteHandlerInter inter = executeHandlers.get(keyCodeHash);
        if(!isRunning){
            return inter;
        }
        if(inter instanceof BackHandler){
            return inter;
        }
        return null;
    }
}
