import base.SetBaseServer;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.List;

import static tools.APITools.get;
import static tools.MyAssert.assertMatch;

/**
 * Created by Administrator on 2017/4/27.
 */
@Title("驾到话题相关接口")
public class JdTopicResource extends SetBaseServer {

    @Title("话题免认证查询接口")
    @Description("验证查询是否成功，将所有话题ID保存供后期使用")
    @Test
    public void getTopics() throws Exception {

//        Response response =when().
//                get("/jiadao/api/public/topic/all").
//                then().
//                statusCode(HttpStatus.SC_OK).
//                body("code",equalTo("0")).
//                body("msg",equalTo("成功")).
//                extract().response();

        Response response = get("/jiadao/api/public/topic/all");

        if (response.statusCode() == 200) {
            assertMatch(response, "msg", "成功");
            assertMatch(response, "code", "0");
        }else if(response.statusCode() == 502){
            Assertions.fail("网关出错");
        }else {
            Assertions.fail("请求失败");
        }


        //获取所有话题信息，供后期使用
        List topics = response.getBody().jsonPath().getList("result*.id");
        saveDatas.put("topic",topics);
//        List<String> a = (List) saveDatas.get("topic");
//        String b = a.get(new Random().nextInt(a.size()));
//        System.out.println(b);
//        System.out.println(saveDatas.get("topic"));

    }
}
