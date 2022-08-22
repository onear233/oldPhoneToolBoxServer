package live.midreamsheep.optb.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.data.SocketConfig;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;
import live.midreamsheep.optb.function.ip.IpAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class GetFileFromPhone implements ExecuteHandlerInter {

    public static boolean isRunning = false;

    @Override
    public void execute() {
        if(isRunning){
            return;
        }
        new Thread(()-> {
            try {
                isRunning = true;
                sendIpAddressToPhone(IpAddress.getIpAddress());
                DownloadFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void sendIpAddressToPhone(String ipAddress) throws IOException {
        System.out.println(ipAddress);
        byte[] data = ipAddress.getBytes();
        byte[] lengthBytes = new byte[3];
        for (int i = 0; i < 2; i++) {
            int offset = (lengthBytes.length - 2 - i) * 8;
            lengthBytes[i] = (byte) (((short)data.length >>> offset) & 0xff);
        }
        lengthBytes[2] = 0x04;
        SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(lengthBytes));
        SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(data));
    }

    private void DownloadFile(){
        try (ServerSocketChannel socketChannel = ServerSocketChannel.open()) {
            socketChannel.bind(new InetSocketAddress(SocketConfig.DOWNLOAD_PORT));
            SocketChannel accept = socketChannel.accept();//结束数量
            ByteBuffer buffer = ByteBuffer.allocate(1);
            accept.read(buffer);
            accept.close();
            int count = buffer.array()[0];
            System.out.println(count);
            for(int i = 0; i< count; i++){
                SocketChannel channel = socketChannel.accept();
                DownloadAFile(channel);
                channel.close();
            }
            isRunning = false;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void DownloadAFile(SocketChannel socket) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        socket.read(byteBuffer);
        byte[] array = byteBuffer.array();
        byteBuffer = ByteBuffer.allocate((array[0] & 0xFF) << 8 | (array[1] & 0xFF));
        socket.read(byteBuffer);
        array = byteBuffer.array();
        String fileName = new String(array);
        System.out.println(fileName);
        byteBuffer = ByteBuffer.allocate(1024);
        //获取输出流
        if(!SocketConfig.DOWNLOAD_FILE.exists()){
            SocketConfig.DOWNLOAD_FILE.mkdirs();
        }
        try (OutputStream os = new FileOutputStream(new File(SocketConfig.DOWNLOAD_FILE,fileName))) {
            while (socket.read(byteBuffer) != -1) {
                byteBuffer.flip();
                os.write(byteBuffer.array(), 0, byteBuffer.limit());
                byteBuffer.clear();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
    }
}
