package live.midreamsheep.optb.function.window.api;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class WindowMonitor {

    public static WindowsBean getWindowsBean() {
        WindowsBean bean = new WindowsBean();
        setWindowTitle(bean);
        setCpuUsageAndCount(bean);
        setMemoryUsageAndTotal(bean);
        setProcessesCount(bean);
        return bean;
    }

    private static void setMemoryUsageAndTotal(WindowsBean bean) {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();
        bean.setMemoryTotal(new  DecimalFormat("#.##").format(totalByte/1024.0/1024.0/1024.0)+"GB");
        bean.setMemoryUsage(new DecimalFormat("#.##%").format((totalByte-acaliableByte)*1.0/totalByte));
    }


    /**
     * 获取系统名
     */
    private static void setWindowTitle(WindowsBean windowsBean) {
        //获取系统信息
        windowsBean.setWindowTitle(System.getProperty("os.name"));
    }

    /**
     * 获取CPU占用率
     */
    private static void setCpuUsageAndCount(WindowsBean bean) {
        //System.out.println("----------------cpu信息----------------");
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        bean.setCpuCount(""+processor.getLogicalProcessorCount());
         bean.setCpuUsage(new DecimalFormat("#.##%").format(1.0-(idle * 1.0 / totalCpu)));
    }
    private static void setProcessesCount(WindowsBean bean){
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        bean.setNumberOfProcesses(os.getProcessCount()+"");
    }
}
