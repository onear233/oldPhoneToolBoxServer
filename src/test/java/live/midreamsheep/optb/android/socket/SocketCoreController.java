package live.midreamsheep.optb.android.socket;


import live.midreamsheep.optb.android.handler.OPTBHandlerCache;
import live.midreamsheep.optb.android.socket.tool.IpAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketCoreController {


    private final ByteBuffer metaData = ByteBuffer.allocate(3);


    public void starter() throws IOException {
        //建立通道
        ServerSocketChannel socketChannel  = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(7741));
        while (true){
            SocketChannel accept = socketChannel.accept();
            System.out.println("建立连接");
            channelProcess(accept);
        }
    }
    private void channelProcess(SocketChannel client) throws IOException {
        while (true){
            //等待读取
            client.read(metaData);
            System.out.println("读取成功");
            byte[] array = getMetaDataBytes(metaData);
            //获取携带数据长度
            ByteBuffer byteBuffer = ByteBuffer.allocate(array[0] << 8 | array[1]);
            client.read(byteBuffer);
            OPTBHandlerCache.getHandler(array[2]).execute(getMetaDataBytes(byteBuffer));
        }
    }
    private byte[] getMetaDataBytes(ByteBuffer byteBuffer){
        byteBuffer.flip();
        byte[] array = byteBuffer.array();
        byteBuffer.clear();
        return array;
    }

}

