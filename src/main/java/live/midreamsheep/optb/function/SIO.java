package live.midreamsheep.optb.function;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SIO {
    public static String inputString(String filePath){
        try {
            return inputStringByStream(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String inputStringByStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != -1){
            sb.append(new String(buffer,0,len));
        }
        is.close();
        return sb.toString();
    }
}
