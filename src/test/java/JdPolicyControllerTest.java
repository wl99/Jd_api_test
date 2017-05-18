
import base.SetBaseServer;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Title;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Administrator on 2017/4/26.
 */
@Features("新增删除保单")
@Title("驾到保单相关接口")
public class JdPolicyControllerTest extends SetBaseServer {

    private String USER_TOKEN = null;
    private String loginNo = super.loginNo;

    @Before
    public void setUp() {
        USER_TOKEN = getSaveData(loginNo + "accessToken").toString();
    }


    @Title("新增图片保单")
    @Description("接口仅上传图片路径至数据库，须与上传文件接口结合" +
            "\n步骤：" +
            "\n1、新增照片保单" +
            "\n2、验证返回数据" +
            "\n3、获取新增的保单ID" +
            "\n4、操作删除保单接口删除该ID的保单")
    @Test
    public void saveImagePolicyTest() {
        String json = "{\n" +
                "    \"insuranceAttributeValue\":\"0\",\n" +
                "    \"insuranceTypeValue\":\"3\",\n" +
                "    \"photoUrls\":[\n" +
                "        \"/test/2017-04-26/149319745107363.jpg\"\n" +
                "    ]\n" +
                "}";

        //发送请求并获取保单ID
        String policyID = given().
                        contentType(ContentType.JSON).
                        header("Authorization", USER_TOKEN).
                        body(json).
                when().
                        post("/jiadao/api/policyController/savePolicy").
                then().
                        statusCode(200).
                        body("code", equalTo("0")).
                        body("msg", equalTo("操作成功")).
                extract().
                        jsonPath().getString("result.id");

        //删除保单数据
        given().
                contentType(ContentType.JSON).
                header("Authorization",USER_TOKEN).
        when().
                delete("/jiadao/api/jdPolicy/" + policyID).
        then().
                statusCode(200).
                body("code",equalTo("0"));
    }

    //TODO 手动新增保单

}
