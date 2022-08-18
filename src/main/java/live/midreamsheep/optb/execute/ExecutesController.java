package live.midreamsheep.optb.execute;

import live.midreamsheep.optb.execute.tool.Translation;

import java.util.HashMap;
import java.util.Map;

public class ExecutesController {
    private static final Map<String,ExecuteHandlerInter> executeHandlers = new HashMap<>();
    static{
        executeHandlers.put("294556",new Translation());
    }

    public static ExecuteHandlerInter getExecuteHandlers(String keyCodeHash) {
        return executeHandlers.get(keyCodeHash);
    }
}
