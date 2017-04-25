package base;

import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.writeFile;


/**
 * Created by Administrator on 2017/4/10.
 */
public class GetAdminToken extends SetBaseServer {

    public  void getAdmintoken() throws JsonPathException, IOException {
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
//        String path = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;

//        System.out.println(path);
        writeFile("adminToken.json", TOKEN);

//        String adminToken = readFile(path + "adminToken.json");
//        System.out.println(adminToken);

    }


}
