package live.midreamsheep.optb.android;

import live.midreamsheep.optb.android.socket.SocketCoreController;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println(getIpAddress());
        ServerSocket socket = new ServerSocket(7741);
        socket.accept();
        System.out.println("建立连接");
    }
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (!(ip instanceof Inet4Address)) {
                        continue;
                    }
                    String address = ip.getHostAddress();
                    return address;
                }
            }
        } catch (Exception e) {

        }
        return "";
    }
}
