package live.midreamsheep.optb.executes;

import java.util.HashMap;
import java.util.Map;

public class ExecutesController {
    private static final Map<String,ExecuteHandlerInter> executeHandlers = new HashMap<>();
    public static boolean isRunning = false;
    public static ExecuteHandlerInter bake;

    public static ExecuteHandlerInter getExecuteHandlers(String keyCodeHash) {
        ExecuteHandlerInter inter = executeHandlers.get(keyCodeHash);
        if(!isRunning){
            return inter;
        }
        if(inter == bake){
            return inter;
        }
        return null;
    }
    public static void putExecuteHandlers(String keyCodeHash,ExecuteHandlerInter inter) {
        executeHandlers.put(keyCodeHash,inter);
    }
}
