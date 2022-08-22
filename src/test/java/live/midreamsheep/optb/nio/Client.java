package live.midreamsheep.optb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",5555));
        System.out.println("连接已经建立");
        ByteBuffer buffer = ByteBuffer.allocate("hello".getBytes().length);
        socketChannel.read(buffer);
        System.out.println(new String(buffer.array()));
    }
}
