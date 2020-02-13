package rewards.internal.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rewards.internal.monitor.Monitor;
import rewards.internal.monitor.MonitorFactory;

// 	TODO-02: REQUIREMENT #1: Use AOP to log a message before
//           any repository find...() method is invoked.
//  - Indicate this class is an aspect.
//	- Also mark it as a component.
//	- Optionally place @Autowired annotation on the constructor
//    where `MonitorFactory` dependency is being injected.
//    (It is optional since there is only a single constructor in the class.)

public class LoggingAspect {
    public final static String BEFORE = "'Before'";
    public final static String AROUND = "'Around'";

	private Logger logger = LoggerFactory.getLogger(getClass());
	private MonitorFactory monitorFactory;

	
	public LoggingAspect(MonitorFactory monitorFactory) {
		super();
		this.monitorFactory = monitorFactory;
	}


	//	TODO-03: Write Pointcut Expression
	//	- Decide which advice type is most appropriate to the requirement.
	//  - Write a pointcut expression that selects only find* methods on
	//    our Repository classes.
    //
	//  Note: The pointcut expression can be very hard to work out. If
	//  you get stuck, refer to the examples in the slides or read the
    //  detailed instructions in the lab-notes.

	public void implLogging(JoinPoint joinPoint) {
		// Do not modify this log message or the test will fail
		logger.info(BEFORE + " advice implementation - " + joinPoint.getTarget().getClass() + //
				"; Executing before " + joinPoint.getSignature().getName() + //
				"() method");
	}
	
	
    //	TODO-07: REQUIREMENT #2: Use AOP to time update...() methods.
    //
    //  Mark this method as around advice.  Write a pointcut
	//	expression to match on all update* methods on Repository classes.
	//
	//  HINT: Again, the pointcut expression can be hard to work out, so if
	//  you get stuck, refer to the pointcut expression you wrote above for 
	//  implLogging(), this one is similar.
	// 
	//  If you are really stuck, PLEASE ask a colleague or your instructor.

	public Object monitor(ProceedingJoinPoint repositoryMethod) throws Throwable {
		String name = createJoinPointTraceName(repositoryMethod);
		Monitor monitor = monitorFactory.start(name);
		try {
			// Invoke repository method ...
			
			//  TODO-08: Add the logic to proceed with the target method invocation.
			//  Be sure to return the target method's return value to the caller
			//  and delete the line below.

			return new String("Delete this line after completing TODO-08");

		} finally {
			monitor.stop();
			// Do not modify this log message or the test will fail
			logger.info(AROUND + " advice implementation - " + monitor);
		}
	}
		
	private String createJoinPointTraceName(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		StringBuilder sb = new StringBuilder();
		sb.append(signature.getDeclaringType().getSimpleName());
		sb.append('.').append(signature.getName());
		return sb.toString();
	} 
}