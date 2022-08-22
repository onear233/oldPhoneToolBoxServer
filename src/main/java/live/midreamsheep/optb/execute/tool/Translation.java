package live.midreamsheep.optb.execute.tool;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static java.awt.Toolkit.getDefaultToolkit;

public class Translation implements ExecuteHandlerInter {
    @Override
    public void execute() {
        //获取剪贴板的内容
        String content = null;
        try {
            content = getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
            if(content==null||content.equals("")){
                return;
            }
            System.out.println(content);
            //获取文本长度 2个字节形式表示
            byte[] data = content.getBytes();
            byte[] shortBuf = new byte[3];
            //data的长度转byte
            shortBuf[0] = (byte) (data.length >> 8);
            shortBuf[1] = (byte) (data.length);
            //传输元数据
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(shortBuf));
            //传输文本
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(data));
        } catch (UnsupportedFlavorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}
