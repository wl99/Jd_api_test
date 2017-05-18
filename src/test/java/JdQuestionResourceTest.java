
import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tools.RandomData.getNowTime;
import static tools.RandomData.getNowTime2;

/**
 * Created by Administrator on 2017/4/7.
 */

@Title("问题相关接口测试")
@FixMethodOrder(MethodSorters.DEFAULT)
public class JdQuestionResourceTest extends SetBaseServer {

    private String USER_TOKEN = null;
    private String loginNo = super.loginNo;
    private String adminToken = null;

    @Before
    public void setUp() throws Exception {
        USER_TOKEN = getSaveData(loginNo + "accessToken").toString();
        adminToken = getSaveData("adminToken").toString();
    }

    @Title("用户新增问题接口")
    @Description("用户新增问题，验证是否成功")
    @Test
    public void addQuestionTest() throws Exception {


        //读取文件获取最新的话题
        /**
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
         */
        List<String> a = (List) saveDatas.get("topic");
        String b = a.get(new Random().nextInt(a.size()));



        String questionDTO = "{\"describe\":\"自动化回归测试标题" + getNowTime() + "\"," +
                "\"title\":\"API回归测试数据描述"+ getNowTime2()  +"\"," +
                "\"topicIds\":" +
                "[\""+ b +"\"]}";

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

    @Title("查询我的问题列表")
    @Description("查询我的问题列表，保存列表中的问题id")
    @Test
    public void myQuestions() throws Exception{
        List questions = given().
                contentType(ContentType.JSON).
                header("Authorization",USER_TOKEN).
        when().
                get("/jiadao/api/question/myQuestions").
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                body().jsonPath().
                getList("result*.id");

        saveDatas.put(loginNo + "questionLis",questions);

    }

    @Title("查询我的问题总数统计")
    @Test
    public void countAnswer() throws Exception{
        String loginID = getSaveData(loginNo + "loginID").toString();

        given().
                contentType(ContentType.JSON).
                header("Authorization",USER_TOKEN).
        when().
                get("/jiadao/api/question/count/" + loginID).
        then().
                statusCode(HttpStatus.SC_OK);
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
