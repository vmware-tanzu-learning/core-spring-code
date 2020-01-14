package accounts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// TODO-06: Get yourself familiarized with various testing utility classes
// as described in the lab document

// TODO-07: Use `@WebMvcTest` and `@AutoConfigureDataJpa` annotations
@ExtendWith(SpringExtension.class)
public class AccountControllerBootTests {

	// TODO-08: Autowire MockMvc bean

	// TODO-09: Create `AccountManager` mock bean

	// TODO-12: Experiment with @MockBean vs @Mock
	// - Change `@MockBean` to `@Mock` for the `AccountManager dependency
	// - Run the test and observe a test failure

	// TODO-10: Write positive unit test for GET request for an accont
	@Test
	public void accountDetails() throws Exception {

		// arrange

		// act and assert

		// verify

	}

	// TODO-11: Write negative unit test for GET request for an account
	@Test
	public void accountDetailsFail() throws Exception {



	}

    // TODO-13: Write unit test for `POST` request for an account
	@Test
	public void createAccount() throws Exception {



	}


    // Utility class for converting an object into JSON string
	protected static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
