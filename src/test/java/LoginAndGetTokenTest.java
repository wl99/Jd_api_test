
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;
import base.SetBaseServer;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by Administrator on 2017/4/26.
 */

@Title("登录系统获取用户Token")
public class LoginAndGetTokenTest extends SetBaseServer {
    private String TOKEN = null;
    private String loginNo = super.loginNo;

    @Before
    public void setUp() {
        TOKEN = getSaveData("adminToken").toString();
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
                        get("jdcommonservice/api/findSmsRecords?phoneNumber=" + loginNo).
                then().
                        statusCode(200).
                extract().
                        jsonPath().
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
                statusCode(HttpStatus.SC_OK).
                extract().response();

        //response1.getBody().prettyPrint();
        String userAccessToken = "Bearer " + response1.getBody().jsonPath().getString("result.access_token");
        String userRefreshToken = response1.getBody().jsonPath().getString("result.refresh_token");

        //存入公共参数中
        saveDatas.put(loginNo + "accessToken",userAccessToken);
        saveDatas.put(loginNo + "refreshToken",userRefreshToken);
//        System.out.println(saveDatas);

//        WriteAndReadFile.writeFile(loginNo + "_AccessToken.json", userAccessToken);
//
//        //刷新Token写入文件
//        WriteAndReadFile.writeFile(loginNo + "_RefreshToken.json",userRefreshToken);

    }
}
