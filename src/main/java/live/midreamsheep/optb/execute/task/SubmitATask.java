package live.midreamsheep.optb.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.execute.ExecuteHandlerInter;

import java.awt.datatransfer.DataFlavor;
import java.nio.ByteBuffer;

import static java.awt.Toolkit.getDefaultToolkit;

public class SubmitATask implements ExecuteHandlerInter {
    @Override
    public void execute() {
        String content;
        try {
            content = getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
            if(content==null||content.equals("")){
                return;
            }
            //判断是否符合正则表达式
            if(!content.matches("(https|http)?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)")){
                return;
            }
            //获取文本长度 2个字节形式表示
            byte[] data = content.getBytes();
            byte[] shortBuf = new byte[3];
            for (int i = 0; i < 2; i++) {
                int offset = (shortBuf.length - 2 - i) * 8;
                shortBuf[i] = (byte) (((short)data.length >>> offset) & 0xff);
            }
            shortBuf[2] = 0x03;
            //传输元数据
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(shortBuf));
            //传输文本
            SocketChannelStatic.socketChannel.write(ByteBuffer.wrap(data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){}
}