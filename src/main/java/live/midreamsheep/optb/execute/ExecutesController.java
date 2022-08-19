package live.midreamsheep.optb.execute;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.tool.Translation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ExecutesController {
    private static final Map<String,ExecuteHandlerInter> executeHandlers = new HashMap<>();
    static{
        executeHandlers.put("4556",new Translation());
        executeHandlers.put("2948",()->{
            try {
                SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(new byte[]{0x00,0x00,0x01}));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static ExecuteHandlerInter getExecuteHandlers(String keyCodeHash) {
        return executeHandlers.get(keyCodeHash);
    }
}
