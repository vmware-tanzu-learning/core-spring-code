package rewards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JdbcBootApplicationTests {
    public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testNumberOfAccount() {

        long count = jdbcTemplate.queryForObject(QUERY, Long.class);

        assertThat(count).isEqualTo(21L);
    }

}
