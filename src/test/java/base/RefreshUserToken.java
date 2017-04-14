package base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static base.GetUserToken.getUserToken;
import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.readFile;
import static support.WriteAndReadFile.writeFile;

/**
 * Created by Administrator on 2017/4/12.
 */
public class RefreshUserToken extends SetBaseServer {

    public void refreshUserToken(String loginNo) throws Exception {
        String path = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        String userRefreshToken = readFile(path + loginNo + "_RefreshToken.json").toString();
        Response response = given().
                    contentType(ContentType.JSON).
                    header("Accept", "application/json").
                    param("refreshToken", userRefreshToken).
                when().
                    post("/jiadao/api/public/refreshToken").
                then().
                    statusCode(200).
                extract().
                    response();
        //如果刷新token过期，重新登录，不然刷新token
        if (response.getBody().jsonPath().get("code") == "400401") {
            //重新登录
            getUserToken(loginNo);
        }else {
            //获取accessToken，refreshToken，并更新文件
            writeFile(path + loginNo + "_AccessToken",response.getBody().jsonPath().getString("result.access_token"));
            writeFile(path + loginNo + "_RefreshToken",response.getBody().jsonPath().getString("result.refresh_token"));

        }

    }
}
