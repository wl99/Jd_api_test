import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Title;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static support.WriteAndReadFile.readFile;

/**
 * Created by Administrator on 2017/4/27.
 */

@Title("保险课堂相关接口")
public class JdClassroomTest extends SetBaseServer{
    //TODO 新增接口、删除接口
    //TODO 需参数化主讲人信息，话题

    String token = null;

    @Before
    public void setUp() throws Exception{
        token = readFile("adminToken.json");
    }

    @Test
    public void addClassroom() throws Exception{
        String json = "{\n" +
                "    \"coverImages\":\"http://onz6cpgv7.bkt.clouddn.com/2017-04-27/1493273219887.jpeg\",\n" +
                "    \"createUserId\":\"tb_586b673312839400081d4e8c\",\n" +
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

        given().
                contentType(ContentType.JSON).
                header("Authorization",token).
                body(json).
        when().
                post("/jiadao/api/classroom/").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo("0")).
                body("msg",equalTo("操作成功"));

    }
}
