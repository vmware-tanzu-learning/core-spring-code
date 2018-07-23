package rewards;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JdbcBootApplicationTests {
	public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

	@Autowired JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		System.out.println("setting up in junit 5");
	}

	@Test
	public void testNumberAccount() throws Exception {

		long count = jdbcTemplate.queryForObject(QUERY, Long.class);

		Assert.assertEquals(21L, count);
	}

}
