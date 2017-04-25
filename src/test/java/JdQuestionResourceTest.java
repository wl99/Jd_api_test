import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import static base.GetUserToken.getUserToken;
import static io.restassured.RestAssured.given;
import static support.WriteAndReadFile.readFile;

/**
 * Created by Administrator on 2017/4/7.
 */

@Title("问题相关接口测试")
public class JdQuestionResourceTest extends SetBaseServer {

    String USER_TOKEN = null;
    String loginNo = "18606535378";
 //   private DBCollection col;


    @Before
    public void setUp() throws Exception {
        getUserToken(loginNo);
//        String path = System.getProperty("user.dir") + File.separator + "jsondir" + File.separator;
        USER_TOKEN = readFile(loginNo + "_AccessToken.json");

//        // 连接到 mongodb 服务
//        MongoClient mongoClient = new MongoClient("192.168.0.202", 27018);
//
//        // 连接到数据库
//        //MongoDatabase mongoDatabase = mongoClient.getDatabase("jiadao");
//        DB datebase = mongoClient.getDB("jiadao");
//
//        //连接表
//        col = datebase.getCollection("jd_question");
    }

    @Title("新增问题接口")
    @Description("新增问题，验证是否成功")
    @Test
    public void addQuestionTest() throws Exception {


        String questionDTO = "{\"describe\":\"这是自动化回归测试生成数据\",\"title\":\"API回归测试数据\",\"topicIds\":[\"58c20e0356cf87300ae04afe\"]}";

        given().
                contentType(ContentType.JSON).
                //  header("Accept","application/json").
                header("Authorization", USER_TOKEN).
                body(questionDTO).
        when().
                post("/jiadao/api/question/add").
        then().
                statusCode(200);

//        BasicDBObject query = new BasicDBObject("title","API回归测试数据");
//        DBObject cursor = col.findOne(query);
//
//        System.out.println(cursor);


    }

    @Title("删除问题接口")
    @Description("使用不存在的问题ID，验证返回信息")
    @Test()
    public void delQuestion() throws Exception {

        given().
                contentType(ContentType.JSON).
        when().
                delete("jiadao/api/question/2").
        then().
                statusCode(200);
    }


}
