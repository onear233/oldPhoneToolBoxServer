package live.midreamsheep.optb.function.window.api;

public class WindowsBean {
    private String windowTitle;
    private String cpuUsage;
    private String cpuCount;
    private String memoryUsage;
    private String memoryTotal;

    @Override
    public String toString() {
        return "WindowsBean{" +
                "windowTitle='" + windowTitle + '\'' +
                ", cpuUsage='" + cpuUsage + '\'' +
                ", cpuCount='" + cpuCount + '\'' +
                ", memoryUsage='" + memoryUsage + '\'' +
                ", memoryTotal='" + memoryTotal + '\'' +
                ", numberOfProcesses='" + numberOfProcesses + '\'' +
                '}';
    }

    private String numberOfProcesses;

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(String cpuCount) {
        this.cpuCount = cpuCount;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public String getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public void setNumberOfProcesses(String numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;
    }
}
