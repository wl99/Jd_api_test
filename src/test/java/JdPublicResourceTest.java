import base.SetBaseServer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by Administrator on 2017/4/6.
 */

@Title("驾到免认证服务接口")
@FixMethodOrder(MethodSorters.DEFAULT)
public class JdPublicResourceTest extends SetBaseServer {

    private String loginNo = super.loginNo;

    @Title("验证发送验证码接口")
    @Description("使用18606535378发送验证码，验证是否发送成功")
    @Test
    public void sendLoginVerifyCodeTest() throws Exception {
        final String json;
        json = "{\"mobilePhone\":\"" + loginNo +"\"}";

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
    @Title("错误参数刷新TOKEN接口")
    @Description("使用错误参数error调用接口，并验证返回信息")
    @Test
    public void sendErrorRefreshTokenTest() throws Exception {
        when().
                post("/jiadao/api/public/refreshToken?refreshToken=error").
        then().
                statusCode(200).
        and().
                body("code",equalTo("400401")).
                body("msg",equalTo("refresh_token不合法或已过期"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Title("验证是否刷新Token成功")
    @Description("使用18606535378帐号正确未过期的Token刷新，并验证返回信息")
    @Test
    public void sendRightRefreshTokenTest() throws Exception {
        String RefreshToken = getSaveData(loginNo + "refreshToken").toString();

        Response response =given().
                                    contentType(ContentType.JSON).
                                    queryParam("refreshToken",RefreshToken).
                            when().
                                    post("/jiadao/api/public/refreshToken").
                            then().
                                    statusCode(200).
                                    body("code",equalTo("0")).
                                    body("msg",equalTo("操作成功")).
                            extract().
                                    response();


        String accessToken = "Bearer " + response.getBody().jsonPath().getString("result.access_token");
        String refreshToken = response.getBody().jsonPath().getString("result.refresh_token");

        saveDatas.put("accessToken",accessToken);
        saveDatas.put("refreshToken",refreshToken);

        System.out.println(saveDatas);

    }

    @Title("获取驾到所有保险类型分类")
    @Description("查询保险类型分类")
    @Test
    public void searchAllInsuranceTypesTest() throws Exception {

        when().
                get("/jiadao/api/public/insuranceTypes").
        then().
                statusCode(HttpStatus.SC_OK).
        and().
                body("code",equalTo("0")).
                body("msg",equalTo("操作成功")).
                body("result*.name",hasItems("意外险","健康险"));
    }

    @Title("获取驾到所有业务码")
    @Description("查询保驾到所有业务码")
    @Test
    public void searchAllJdBusinessCodes() throws Exception {

        when().
                get("/jiadao/api/public/jdBusinessCodes").
        then().
                statusCode(HttpStatus.SC_OK).
        and().
                body("code",equalTo("0")).
                body("msg",equalTo("操作成功"));
    }
}
