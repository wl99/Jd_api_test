import base.SetBaseServer;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Administrator on 2017/4/6.
 */

@Title("驾到免认证服务接口")
public class JdPublicResourceTest extends SetBaseServer {

    @Title("验证发送验证码接口")
    @Description("使用18606535378发送验证码，验证是否发送成功")
    @Test
    public void sendLoginVerifyCodeTest() throws Exception {
        final String json;
        json = "{\"mobilePhone\":\"18606535378\"}";

        given().
                contentType("application/json").
                body(json).
        when().
                post("/jiadao/api/public/sendLoginVerifyCode").
        then().
                statusCode(200).
        and().
                body("code",equalTo("0")).
                body("msg",equalTo("操作成功"));
    }

    @Title("验证发送验证码接口")
    @Description("使用错误手机号1860653537发送验证码并验证返回信息")
    @Test
    public void sendErrorPhoneNumTest() throws Exception {
        final String json;
        json = "{\"mobilePhone\":\"1860653537\"}";

        given().
                contentType("application/json").
                body(json).
        when().
                post("/jiadao/api/public/sendLoginVerifyCode").
        then().
                statusCode(200).
        and().
                body("code",equalTo("400")).
                body("msg",equalTo("非有效手机号码"));
    }

    @Severity(SeverityLevel.BLOCKER)
    @Title("刷新TOKEN接口")
    @Description("使用错误参数调用接口，并验证返回信息")
    @Test
    public void sendErrorRefreshTokenTest() {
        when().
                post("/jiadao/api/public/refreshToken?refreshToken=error").
        then().
                statusCode(200).
        and().
                body("code",equalTo("400401")).
                body("msg",equalTo("refresh_token不合法或已过期"));
    }
}
