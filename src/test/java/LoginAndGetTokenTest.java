import base.SetBaseServer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.readFile;
import static support.WriteAndReadFile.writeFile;

/**
 * Created by Administrator on 2017/4/26.
 */

@Title("登录系统获取用户Token")
public class LoginAndGetTokenTest extends SetBaseServer {
    String TOKEN = null;
    String loginNo = "18606535378";

    @Before
    public void setUp() {
        TOKEN = readFile("adminToken.json");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Title("使用18606535378帐号登录，获取Token")
    @Description("验证步骤：" +
            "\n1、发送验证码" +
            "\n2、使用接口获取最近的短信验证码" +
            "\n3、登录获取Token" +
            "\n4、将Token写入文件供后面的接口使用")
    @Test
    public void loginAndGetUserToken() throws Exception{
        String phoneNo = "{\"mobilePhone\":\"" + loginNo + "\"}";

        given().
                contentType(ContentType.JSON).
                header("Accept", "application/json").
                body(phoneNo).
        when().
                post("/jiadao/api/public/sendLoginVerifyCode").
        then().
                statusCode(200);

        //查询并保存验证码验证码
        String verifyCode = given().
                        header("Authorization", TOKEN).
                when().
                        get("/commonservice/api/findSmsRecords?phoneNumber=" + loginNo).
                then().
                        extract().jsonPath().
                        getString("[0].smsContent").
                        substring(0, 6);

        //登录获取用户token
        Map<String, Object> json = new HashMap<>();
        json.put("mobilePhone", loginNo);
        json.put("verifyCode", verifyCode);
        //String json = "{\"mobilePhone\": " + loginNo.toString() + ",\"verifyCode\":" + verifyCode.toString() + "}";

        Response response1 = given().
                header("Content-Type", "application/json").
                header("Authorization", TOKEN).
                body(json).
                when().
                post("/jiadao/api/public/verifyCodeLogin").
                then().
                extract().response();

        //response1.getBody().prettyPrint();
        String userAccessToken = "Bearer " + response1.getBody().jsonPath().getString("result.access_token");
        String userRefreshToken = response1.getBody().jsonPath().getString("result.refresh_token");

        writeFile(loginNo + "_AccessToken.json", userAccessToken);

        //刷新Token写入文件
        writeFile(loginNo + "_RefreshToken.json",userRefreshToken);

    }
}
