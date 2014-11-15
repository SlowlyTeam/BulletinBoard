import org.apache.log4j.Logger;
import org.junit.Test;

public class TestClassForLog4jTest {

    final static Logger logger = Logger.getLogger(TestClassForLog4j.class);

    @Test
    public void testMain() throws Exception {
        logger.info("Test logger in JUnit");
    }
}