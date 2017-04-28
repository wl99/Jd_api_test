import base.SetBaseServer;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static support.WriteAndReadFile.writeFile;

/**
 * Created by Administrator on 2017/4/27.
 */
@Title("驾到话题相关接口")
public class JdTopicResource extends SetBaseServer {

    @Title("话题免认证查询接口")
    @Description("验证查询是否成功，将所有话题ID保存供后期使用")
    @Test
    public void getTopics() throws Exception{
        Response response =when().
                get("/jiadao/api/public/topic/all").
                then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo("0")).
                body("msg",equalTo("成功")).
                extract().response();

        String topics = response.getBody().jsonPath().getString("result*.id");
        //System.out.println(topics);
        writeFile("allTopics.json",topics);

    }

}
