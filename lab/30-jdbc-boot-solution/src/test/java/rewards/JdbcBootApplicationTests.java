package rewards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JdbcBootApplicationTests {
    public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    public void testNumberOfAccount() throws Exception {

        long count = jdbcTemplate.queryForObject(QUERY, Long.class);

        assertEquals(21L, count);
    }

}
