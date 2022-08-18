package live.midreamsheep.optb.android;

import live.midreamsheep.optb.android.socket.SocketCoreController;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        new SocketCoreController().starter();
    }

}
