package rewards.internal.aspects;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;

import rewards.internal.monitor.jamon.JamonMonitorFactory;

/**
 * Unit test to test the behavior of the RepositoryPerformanceMonitor aspect in isolation.
 */
public class RepositoryPerformanceMonitorTest {

	@Test
	public void testMonitor() throws Throwable {
		JamonMonitorFactory monitorFactory = new JamonMonitorFactory();
		RepositoryPerformanceMonitor performanceMonitor = new RepositoryPerformanceMonitor(monitorFactory);
		Signature signature = createMock(Signature.class);
		ProceedingJoinPoint targetMethod = createMock(ProceedingJoinPoint.class);

		expect(targetMethod.getSignature()).andReturn(signature);
		expect(signature.getDeclaringType()).andReturn(Object.class);
		expect(signature.getName()).andReturn("hashCode");
		expect(targetMethod.proceed()).andReturn(new Object());

		replay(signature, targetMethod);
		performanceMonitor.monitor(targetMethod);
		verify(signature, targetMethod);
	}

}
