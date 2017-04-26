import base.SetBaseServer;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by Administrator on 2017/4/6.
 */
@Title("保险课堂免认证接口API")
public class JdClassroomResourcePublicTest extends SetBaseServer {

    @Title("分页查询所有课堂")
    @Description("使用默认参数查询接口，验证返回信息")
    @Test
    public void searchAllClassroomTest() throws Exception {
        when().
                get("/jiadao/api/public/classroom/").
        then().
                statusCode(200).
                body("code",equalTo("0"));

    }

    @Title("分页查询所有推荐课堂")
    @Description("使用默认参数查询接口，并验证is_recommend的是否为‘true’")
    @Test
    public void searchAllRecommendClassroom() throws Exception {

        when().
                get("/jiadao/api/public/classroom/recommend").
        then().
                statusCode(200).
        and().
                body("code",equalTo("0")).
                body("result*.is_recommend",hasItems(true));


    }

    @Title("查询保险课堂详细信息接口")
    @Description("传入错误的课堂ID，验证接口返回信息")
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
