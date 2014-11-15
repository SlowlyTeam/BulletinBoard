/**
 * Created by Emil Kleszcz on 2014-11-15.
 */

import org.apache.log4j.Logger;

public class TestClassForLog4j {

    final static Logger logger = Logger.getLogger(TestClassForLog4j.class);

    public static void main(String[] args) {
        logger.info("Debug sth: " + args.toString());
    }

}
