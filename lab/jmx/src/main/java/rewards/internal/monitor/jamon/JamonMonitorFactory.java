package rewards.internal.monitor.jamon;

import java.util.Date;

import rewards.internal.monitor.GlobalMonitorStatistics;
import rewards.internal.monitor.Monitor;
import rewards.internal.monitor.MonitorFactory;

import com.jamonapi.MonitorComposite;

// TODO-01: This application should run already.  Run it now by right-clicking
//          on the project and selecting Run As ... Spring Boot App.  Open a
//          browser window to http://localhost:8080. You should see the home page.
//          Stop the application.
//          
// TODO-02: Annotate class with JMX annotation to export the bean with name 
// "statistics:name=monitorFactory" as well as methods and attributes
public class JamonMonitorFactory implements MonitorFactory, GlobalMonitorStatistics {

	private com.jamonapi.MonitorFactoryInterface monitorFactory = com.jamonapi.MonitorFactory.getFactory();

	public Monitor start(String name) {
		JamonMonitor monitor = new JamonMonitor(monitorFactory.start(name));
		return monitor;
	}

	public long getCallsCount() {
		return (long) getMonitors().getHits();
	}

	public long getTotalCallTime() {
		return (long) getMonitors().getTotal();
	}

	public Date getLastAccessTime() {
		return getMonitors().getLastAccess();
	}

	public MonitorComposite getMonitors() {
		return monitorFactory.getRootMonitor();
	}

	public long averageCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getAvg();
	}

	public long callCount(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getHits();
	}

	public long lastCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getLastValue();
	}

	public long maximumCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getMax();
	}

	public long minimumCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getMin();
	}

	public long totalCallTime(String methodName) {
		return (long) monitorFactory.getMonitor(methodName, "ms.").getTotal();
	}

}
