import base.SetBaseServer;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Administrator on 2017/4/6.
 */
public class JdPublicResourceTest extends SetBaseServer {


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
