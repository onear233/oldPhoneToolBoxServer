package live.midreamsheep.optb.execute.monitor;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.data.SocketConfig;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;
import live.midreamsheep.optb.execute.ExecutesController;
import live.midreamsheep.optb.function.window.api.WindowMonitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

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
                throw new RuntimeException(e);
            }
            System.out.println("连接建立");
            String content = "";
            while (true) {
                content = WindowMonitor.getWindowsBean().toString();
                System.out.println(content);
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    //传输文本
                    channel.write(ByteBuffer.wrap(data));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(!ExecutesController.isRunning) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
            }
        }).start();
    }

    @Override
    public void close() {

    }
}
