import io.restassured.RestAssured;
import org.junit.Before;

import static utils.constants.Api.*;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

}
