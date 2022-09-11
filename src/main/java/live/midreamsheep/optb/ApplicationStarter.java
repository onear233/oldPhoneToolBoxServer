package live.midreamsheep.optb;

import live.midreamsheep.optb.config.ConfigTool;
import live.midreamsheep.optb.data.SocketConfig;
import live.midreamsheep.optb.function.SIO;
import live.midreamsheep.optb.keyboard.Listener;
import live.midreamsheep.frame.scanner.annotation.scan.ScannerProcessor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ApplicationStarter {

    public static volatile boolean isFix = false;

    public static void starter(Class<?> config) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NativeHookException {
        //解析配置类
        parseConfigClass(config);
        //读取配置文件
        new ConfigTool().readConfig(SIO.inputStringByStream(Objects.requireNonNull(ApplicationStarter.class.getClassLoader().getResourceAsStream("fileRootPath.txt"))));
        //建立连接 并注册为常量
        connect();
        //启动监听
        startListener();
    }

    private static void parseConfigClass(Class<?> config) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //解析注解
        ScannerProcessor.process(config);
    }

    private static void startListener() throws NativeHookException {
        GlobalScreen.addNativeKeyListener(new Listener());
        GlobalScreen.registerNativeHook();
    }

    private static void connect(){
        while (true) {
            try {
                SocketChannelStatic.socketChannel = SocketChannel.open(new InetSocketAddress(SocketConfig.IP, SocketConfig.PORT));
                if (SocketChannelStatic.socketChannel.isConnected()) {
                    isFix = false;
                    break;
                }
            } catch (IOException e) {
                Logger.getLogger(ApplicationStarter.class.getName()).severe("连接建立失败 错误信息：" + e.getMessage());
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void tryConnect(){
        isFix = true;
        connect();
    }
}
