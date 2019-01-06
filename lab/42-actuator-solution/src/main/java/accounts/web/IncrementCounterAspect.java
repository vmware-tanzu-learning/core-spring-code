package accounts.web;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IncrementCounterAspect {

    private Counter counter;

    public IncrementCounterAspect(MeterRegistry meterRegistry) {
        this.counter = meterRegistry.counter("account.fetchall");
    }

    @Before("@annotation(accounts.web.IncrementCounter)")
    public void increment(){
        counter.increment();
    }
}
