package base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.readFile;
import static support.WriteAndReadFile.writeFile;

/**
 * Created by Administrator on 2017/4/11.
 */
public class GetUserToken extends SetBaseServer {


    public static void getUserToken(String loginNo) throws Exception {

//        String path = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        String TOKEN = readFile("adminToken.json").toString();

//        Map<String, Object> phoneNo = new HashMap<>();
//        phoneNo.put("mobilePhone", loginNo);


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
        writeFile(loginNo + "_RefreshToken.json",userRefreshToken);
    }

}
