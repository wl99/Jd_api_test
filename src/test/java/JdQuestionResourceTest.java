import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static base.GetUserToken.getUserToken;
import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.readFile;

/**
 * Created by Administrator on 2017/4/7.
 */
public class JdQuestionResourceTest extends SetBaseServer {

    String USER_TOKEN = null;
    String loginNo = "18606535378";

    @Before
    public void setUp() throws Exception {
        getUserToken(loginNo);
        String path = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        USER_TOKEN = readFile(path + loginNo + "_AccessToken.json");
    }

    @Test
    public void addQuestionTest() throws Exception {


        String questionDTO = "{\"describe\":\"问题描述2\",\"title\":\"问题标题1\",\"topicIds\":[\"58c20e0356cf87300ae04afe\"]}";

        given().
                contentType(ContentType.JSON).
                //  header("Accept","application/json").
                header("Authorization", USER_TOKEN).
                body(questionDTO).
        when().
                post("/jiadao/api/question/add").
        then().
                statusCode(200);

    }
}
