package live.midreamsheep.optb.executes.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.scanner.annotation.handler.ExecuteHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@ExecuteHandler("2329")
public class MonitorTasks implements ExecuteHandlerInter {

    @Override
    public void execute()  {
        try {
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(new byte[]{0x00,0x00,0x03}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
