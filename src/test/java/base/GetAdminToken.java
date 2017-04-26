package base;

import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.junit.Ignore;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.writeFile;


/**
 * Created by Administrator on 2017/4/10.
 */
@Ignore
public class GetAdminToken extends SetBaseServer {

    public  void getAdminTtoken() throws JsonPathException, IOException {
        //登录admin帐号获取token


        Response response = given().
                contentType("application/x-www-form-urlencoded").header("Authorization", "Basic d2ViX2FwcDo=").
                when().
                post("/tenbentoauth2/oauth/token?username=admin&password=admin&grant_type=password").
                then().
                statusCode(200).
                extract().
                response();
        String ACCES_TOKEN = response.path("access_token");
        String TOKEN = "Bearer " + ACCES_TOKEN;

        writeFile("adminToken.json", TOKEN);
    }


}
