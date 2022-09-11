package live.midreamsheep.optb.executes.execute.back;

import live.midreamsheep.optb.ApplicationStarter;
import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.executes.ExecuteInit;
import live.midreamsheep.optb.executes.ExecutesController;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@ExecuteHandler("2948")
public class BackHandler implements ExecuteHandlerInter, ExecuteInit {
    @Override
    public void execute() {
        ExecutesController.isRunning = false;
        SocketChannelStatic.send(new byte[]{0x00,0x00,0x01});
    }

    @Override
    public void init(String key,ExecuteHandlerInter proxy) {
        ExecutesController.bake = proxy;
    }
}
