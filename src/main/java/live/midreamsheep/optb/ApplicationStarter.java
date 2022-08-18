package live.midreamsheep.optb;

import live.midreamsheep.optb.config.ConfigTool;
import live.midreamsheep.optb.data.SocketConfig;
import live.midreamsheep.optb.function.SIO;
import live.midreamsheep.optb.keyboard.Listener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationStarter {
    public static void main(String[] args) throws NativeHookException {
        //读取配置文件
        new ConfigTool().readConfig(SIO.inputString(Objects.requireNonNull(ApplicationStarter.class.getClassLoader().getResource("fileRootPath.txt")).getFile()));
        //建立连接 并注册为常量
        connect();
        //启动监听
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        GlobalScreen.addNativeKeyListener(new Listener());
        GlobalScreen.registerNativeHook();

    }
    private static void connect(){
        try {
            SocketChannelStatic.socketChannel = SocketChannel.open(new InetSocketAddress(SocketConfig.IP,SocketConfig.PORT));
        } catch (IOException e) {
            Logger.getLogger(ApplicationStarter.class.getName()).severe("连接建立失败 错误信息："+e.getMessage());
        }
    }
}
