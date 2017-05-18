import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tools.RandomData.getNowTime;
import static tools.RandomData.getNowTime2;

/**
 * Created by Administrator on 2017/4/27.
 */

@Title("保险课堂相关接口")
public class JdClassroomTest extends SetBaseServer {
    //TODO 新增接口、删除接口
    //TODO 需参数化主讲人信息，话题

    private String token = null;
    private String loginNo = super.loginNo;
    private String loginID = null;

    @Before
    public void setUp() throws Exception {
        token = getSaveData(loginNo + "accessToken").toString();
        loginID = getSaveData(loginNo + "loginID").toString();
    }

    @Title("新增预告保险课堂")
    @Test
    public void addClassroomTest() throws Exception {

        List<String> a = (List) saveDatas.get("topic");
        String b = a.get(new Random().nextInt(a.size()));
        Map<String, Object> json = new HashMap();

        json.put("coverImages", "http://onz6cpgv7.bkt.clouddn.com/2017-04-27/1493273219887.jpeg");
        json.put("createUserId", loginID);
        json.put("title", "自动添加课堂标题" + getNowTime());
        json.put("description", "课堂描述" + getNowTime2());
        json.put("topics", "[" + b + "]");
        //课堂状态“0”预告、“1”直播、“2”回顾
        json.put("classroomStatus","0");
        json.put("advTime","2017-06-30 14:30:00");
        json.put("liveUrl","");
        json.put("videoUrl","");
        //是否推荐
        json.put("is_recommend",true);

        /**
        String json = "{\n" +
                "    \"coverImages\":\"http://onz6cpgv7.bkt.clouddn.com/2017-04-27/1493273219887.jpeg\",\n" +
                "    \"createUserId\":"+ loginID +",\n" +
                "    \"title\":\"自动添加课堂标题\",\n" +
                "    \"description\":\"课堂描述\",\n" +
                "    \"topics\":[\n" +
                "        \"58ed8303cff47e0008761249\",\n" +
                "        \"58ed837dcff47e000876124a\"\n" +
                "    ],\n" +
                "    \"classroomStatus\":\"0\",\n" +
                "    \"advTime\":\"2017-04-30 14:30:00\",\n" +
                "    \"liveUrl\":\"\",\n" +
                "    \"videoUrl\":\"\",\n" +
                "    \"is_recommend\":true\n" +
                "}";
         */

        given().
                contentType(ContentType.JSON).
                header("Authorization", token).
                body(json).
        when().
                post("/jiadao/api/classroom/").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("0")).
                body("msg", equalTo("操作成功"));

    }
}
