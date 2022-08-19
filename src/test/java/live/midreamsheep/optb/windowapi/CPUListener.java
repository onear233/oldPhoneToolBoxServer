package live.midreamsheep.optb.windowapi;

import live.midreamsheep.optb.function.window.api.WindowMonitor;
import org.junit.jupiter.api.Test;

public class CPUListener {
    @Test
    public void CPUUtilizationMonitoring() throws InterruptedException {
        for (int i = 0;i<10;i++) {
            System.out.println(WindowMonitor.getWindowsBean().toString());
        }
    }
}
