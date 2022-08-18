package live.midreamsheep.optb.function;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

public class SIO {
    public static String inputString(String filePath){
        try(InputStream is = new FileInputStream(filePath)){
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = is.read(buffer)) != -1){
                sb.append(new String(buffer,0,len));
            }
            return sb.toString();
        }catch (Exception e){
            Logger.getLogger(SIO.class.getName()).severe(e.getMessage());
        }
        return "";
    }
}
