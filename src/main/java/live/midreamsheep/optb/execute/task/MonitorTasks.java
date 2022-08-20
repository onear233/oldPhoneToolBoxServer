package live.midreamsheep.optb.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MonitorTasks implements ExecuteHandlerInter {

    @Override
    public void execute()  {
        try {
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(new byte[]{0x00,0x00,0x03}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}
