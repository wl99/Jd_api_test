
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Administrator on 2017/4/26.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetGateWayToken.class,
        LoginAndGetTokenTest.class,
        JdPublicResourceTest.class,
        JdInsuranceCompanyTest.class,
        UserResourceTest.class,
        JdTopicResource.class,
        JdQuestionResourceTest.class,
        JdClassroomResourcePublicTest.class,
        JdPolicyControllerTest.class,
        JdSharePolicyTest.class
})
public class RunAllTestCase {
}
