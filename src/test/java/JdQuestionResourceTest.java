import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static support.WriteAndReadFile.readFile;

/**
 * Created by Administrator on 2017/4/7.
 */

@Title("问题相关接口测试")
public class JdQuestionResourceTest extends SetBaseServer {

    String USER_TOKEN = null;
    String loginNo = "18606535378";
    String time = null;
    String adminToken = null;

    @Before
    public void setUp() throws Exception {
        USER_TOKEN = readFile(loginNo + "_AccessToken.json");
        adminToken = readFile("adminToken.json");
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
        time = dateFormat.format(now);
    }

    @Title("用户新增问题接口")
    @Description("用户新增问题，验证是否成功")
    @Test
    public void addQuestionTest() throws Exception {


        //读取文件获取最新的话题
        String topic = readFile("allTopics.json");
        String a = topic.substring(1,topic.length()-1);
        String[] b = a.split(",");

        //将数组字符串转换为数组
        List<String> list = new ArrayList<>();
        for (int i=0; i < b.length;i++){
            String c = b[i].trim();
            list.add(c);
        }

        //随机获取一个话题
        String useTopic = list.get(new Random().nextInt(list.size()));

        String questionDTO = "{\"describe\":\"这是自动化回归测试生成数据\"," +
                "\"title\":\"API回归测试数据"+ time +"\"," +
                "\"topicIds\":" +
                "[\""+ useTopic +"\"]}";

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

    @Title("删除问题接口")
    @Description("使用不存在的问题ID，验证返回信息")
    @Test()
    public void delQuestion() throws Exception {

        given().
                contentType(ContentType.JSON).
                header("Authorization", adminToken).
        when().
                delete("jiadao/api/question/5901be0cfa4f8c00089c3208").
        then().
                statusCode(200).body("code",equalTo("0"));
    }


}
