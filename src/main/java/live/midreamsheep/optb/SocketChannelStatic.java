package live.midreamsheep.optb;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelStatic {
    public static SocketChannel socketChannel;

    public static void send(byte[] datas){
        try {
            socketChannel.write(ByteBuffer.wrap(datas));
        } catch (IOException e) {
            e.printStackTrace();
            ApplicationStarter.tryConnect();
        }
    }
}
