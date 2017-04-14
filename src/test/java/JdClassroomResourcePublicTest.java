import base.SetBaseServer;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Administrator on 2017/4/6.
 */
public class JdClassroomResourcePublicTest extends SetBaseServer {
    @Test
    public void searchAllClassroomTest() throws Exception {
        when().
                get("/jiadao/api/public/classroom/").
        then().
                statusCode(200).
                body("code",equalTo("0"));

    }

    @Test
    public void searchAllRecommendClassroom() throws Exception {

        when().
                get("/jiadao/api/public/classroom/recommend").
        then().
                statusCode(200).
                body("code",equalTo("0"));

    }

    @Test
    public void notFoundClassroom() throws Exception {

        when().
                get("/jiadao/api/public/classroom/1").
        then().
                statusCode(200).
                body("code",equalTo("200203")).
                body("msg",equalTo("未找到目标课堂"));
    }
}
