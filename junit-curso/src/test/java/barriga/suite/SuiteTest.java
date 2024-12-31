package barriga.suite;


import barriga.service.TransactionServiceTest;
import barriga.service.UserServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Test's Suites")
@SelectClasses(value = {
        TransactionServiceTest.class,
        UserServiceTest.class
})
//@SelectPackages("barriga/service")
public class SuiteTest {
}
