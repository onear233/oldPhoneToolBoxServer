package live.midreamsheep.optb.executes.execute.back;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.executes.ExecutesController;
import live.midreamsheep.optb.scanner.annotation.handler.ExecuteHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@ExecuteHandler("2948")
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

}
