import base.SetBaseServer;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.writeFile;

/**
 * Created by Administrator on 2017/4/26.
 */
@Title("获取网关admin的Token")
public class GetGateWayToken extends SetBaseServer{
    String username = "admin";
    String password = "admin";
    String grant_type = "password";

    @Severity(SeverityLevel.BLOCKER)
    @Title("获取网关admin帐号的Token，供后面使用")
    @Test
    public void getGateWayTokenTest() throws Exception{

        Response response = given().
                contentType("application/x-www-form-urlencoded").
                header("Authorization", "Basic d2ViX2FwcDo=").
                queryParam("username",username).
                queryParam("password",password).
                queryParam("grant_type",grant_type).
        when().
                post("/tenbentoauth2/oauth/token").
        then().
                statusCode(200).
        extract().
                response();

        String ACCES_TOKEN = response.path("access_token");
        String TOKEN = "Bearer " + ACCES_TOKEN;

        try {
            writeFile("adminToken.json", TOKEN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
