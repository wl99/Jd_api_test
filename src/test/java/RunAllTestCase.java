import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Administrator on 2017/4/26.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetGateWayToken.class,
        LoginAndGetTokenTest.class,
        UserResourceTest.class,
        JdTopicResource.class,
        JdPublicResourceTest.class,
        JdQuestionResourceTest.class,
        JdClassroomResourcePublicTest.class,
        JdPolicyControllerTest.class,
        JdSharePolicyTest.class})
public class RunAllTestCase {
}
