package live.midreamsheep.optb.executes.execute.task;

import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static java.awt.Toolkit.getDefaultToolkit;

@ExecuteHandler("2829")
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
            SocketChannelStatic.send(shortBuf);
            //传输文本
            SocketChannelStatic.send(data);
        } catch (IOException | UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        }
    }

}