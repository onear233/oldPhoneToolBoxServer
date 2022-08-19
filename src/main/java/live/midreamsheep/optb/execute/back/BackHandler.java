package live.midreamsheep.optb.execute.back;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;
import live.midreamsheep.optb.execute.ExecutesController;

import java.io.IOException;
import java.nio.ByteBuffer;

public class BackHandler implements ExecuteHandlerInter {
    @Override
    public void execute() {
        try {
            ExecutesController.isRunning = false;
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(new byte[]{0x00,0x00,0x01}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}
