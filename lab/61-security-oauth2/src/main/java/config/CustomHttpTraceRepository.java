package config;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomHttpTraceRepository implements HttpTraceRepository {

    List<HttpTrace> traces = new ArrayList<>();

    @Override
    public List<HttpTrace> findAll() {
        return traces;
    }

    @Override
    public void add(HttpTrace trace) {
        if ("POST".equals(trace.getRequest().getMethod())) {
            traces.add(trace);
        }
    }

}
