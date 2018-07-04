package rewards.internal.monitor.jamon;

import java.util.Date;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import rewards.internal.monitor.GlobalMonitorStatistics;
import rewards.internal.monitor.Monitor;
import rewards.internal.monitor.MonitorFactory;

import com.jamonapi.MonitorComposite;

@ManagedResource(objectName = "statistics:name=monitorFactory")
public class JamonMonitorFactory implements MonitorFactory, GlobalMonitorStatistics {

	private com.jamonapi.MonitorFactoryInterface monitorFactory = com.jamonapi.MonitorFactory.getFactory();

	public Monitor start(String name) {
		JamonMonitor monitor = new JamonMonitor(monitorFactory.start(name));
		return monitor;
	}

	@ManagedAttribute
	public long getCallsCount() {
		return (long) getMonitors().getHits();
	}

	@ManagedAttribute
	public long getTotalCallTime() {
		return (long) getMonitors().getTotal();
	}

	@ManagedAttribute
	public Date getLastAccessTime() {
		return getMonitors().getLastAccess();
	}

	public MonitorComposite getMonitors() {
		return monitorFactory.getRootMonitor();
	}

	@ManagedOperation
	public long averageCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getAvg();
	}

	@ManagedOperation
	public long callCount(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getHits();
	}

	@ManagedOperation
	public long lastCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getLastValue();
	}

	@ManagedOperation
	public long maximumCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getMax();
	}

	@ManagedOperation
	public long minimumCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getMin();
	}

	@ManagedOperation
	public long totalCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getTotal();
	}
}
