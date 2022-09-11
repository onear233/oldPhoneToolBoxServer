package live.midreamsheep.optb.executes.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

@ExecuteHandler("2329")
public class MonitorTasks implements ExecuteHandlerInter {

    @Override
    public void execute()  {
        SocketChannelStatic.send(new byte[]{0x00,0x00,0x03});
    }

}
