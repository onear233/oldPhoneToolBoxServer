package live.midreamsheep.optb.executes.execute.monitor;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.data.SocketConfig;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.executes.ExecutesController;
import live.midreamsheep.optb.function.window.api.WindowMonitor;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

@ExecuteHandler("2950")
public class ComputerMonitor implements ExecuteHandlerInter {
    @Override
    public void execute() {
        try {
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(new byte[]{0x00,0x00,0x02}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(()-> {
            ExecutesController.isRunning = true;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            SocketChannel channel;
            try {
                //建立通道
                channel = SocketChannel.open(new InetSocketAddress(SocketConfig.IP,SocketConfig.MONITOR_PORT));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("连接建立");
            String content;
            while (true) {
                content = WindowMonitor.getWindowsBean().toString();
                //获取文本长度 2个字节形式表示
                byte[] data = content.getBytes();
                byte[] shortBuf = new byte[2];
                for (int i = 0; i < 2; i++) {
                    int offset = (shortBuf.length - 1 - i) * 8;
                    shortBuf[i] = (byte) (((short) data.length >>> offset) & 0xff);
                }
                //传输元数据
                try {
                    channel.write(ByteBuffer.wrap(shortBuf));
                    //传输文本
                    channel.write(ByteBuffer.wrap(data));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if(!ExecutesController.isRunning) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }).start();
    }

}
