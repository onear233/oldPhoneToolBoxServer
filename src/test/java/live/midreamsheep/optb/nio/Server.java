package live.midreamsheep.optb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(5555));
        SocketChannel accept = serverSocketChannel.accept();
        System.out.println("连接建立成功");
        accept.write(ByteBuffer.wrap("hello".getBytes()));
    }
}
