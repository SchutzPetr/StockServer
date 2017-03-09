package cz.schutzpetr.stock.core.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by Petr Schutz on 05.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class CPULoad {

    private static long prevUpTime, prevProcessCpuTime;

    private static RuntimeMXBean rmBean;

    private static com.sun.management.OperatingSystemMXBean sunOSMBean;

    private static Result result;

    private static class Result {
        long upTime = -1L;
        long processCpuTime = -1L;
        float cpuUsage = 0;
        int nCPUs;
    }

    private static CPULoad ourInstance = new CPULoad();

    public static CPULoad getInstance() {
        return ourInstance;
    }

    private CPULoad() {
    }

    static {
        try {
            rmBean = ManagementFactory.getRuntimeMXBean();
            sunOSMBean = ManagementFactory.newPlatformMXBeanProxy(
                    ManagementFactory.getPlatformMBeanServer(),
                    ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                    com.sun.management.OperatingSystemMXBean.class
            );

            result = new Result();
            result.nCPUs = sunOSMBean.getAvailableProcessors();
            result.upTime = rmBean.getUptime();
            result.processCpuTime = sunOSMBean.getProcessCpuTime();

        } catch (Exception e) {
            System.err.println(CPULoad.class.getSimpleName() + " exception: " + e.getMessage());
        }
    }

    public float getCPULoad() {

        result.upTime = rmBean.getUptime();
        result.processCpuTime = sunOSMBean.getProcessCpuTime();

        if (result.upTime > 0L && result.processCpuTime >= 0L)
            updateCPUInfo();

        return result.cpuUsage;

    }

    public void updateCPUInfo() {
        if (prevUpTime > 0L && result.upTime > prevUpTime) {
            // elapsedCpu is in ns and elapsedTime is in ms.
            long elapsedCpu = result.processCpuTime - prevProcessCpuTime;
            long elapsedTime = result.upTime - prevUpTime;
            // cpuUsage could go higher than 100% because elapsedTime
            // and elapsedCpu are not fetched simultaneously. Limit to
            // 99% to avoid Plotter showing a scale from 0% to 200%.
            result.cpuUsage =
                    Math.round(
                            Math.min(100F,
                                    elapsedCpu / (elapsedTime * 10000F * result.nCPUs)
                            )
                    );
        }

        prevUpTime = result.upTime;
        prevProcessCpuTime = result.processCpuTime;
    }

}
