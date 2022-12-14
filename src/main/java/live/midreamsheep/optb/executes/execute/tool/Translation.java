package live.midreamsheep.optb.executes.execute.tool;

import live.midreamsheep.optb.ApplicationStarter;
import live.midreamsheep.optb.SocketChannelStatic;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static java.awt.Toolkit.getDefaultToolkit;

@ExecuteHandler("294246")
public class Translation implements ExecuteHandlerInter {
    @Override
    public void execute() {
        //获取剪贴板的内容
        String content;
        try {
            content = getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
            if(content==null||content.equals("")){
                return;
            }
            System.out.println("    需要翻译的文本"+content);
            //获取文本长度 2个字节形式表示
            byte[] data = content.getBytes();
            byte[] shortBuf = new byte[3];
            //data的长度转byte
            shortBuf[0] = (byte) (data.length >> 8);
            shortBuf[1] = (byte) (data.length);
            //传输元数据
            SocketChannelStatic.send(shortBuf);
            //传输文本
            SocketChannelStatic.send(data);
            System.out.println("    传输完毕");
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
            ApplicationStarter.tryConnect();
        }
    }

}
