
import base.SetBaseServer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tools.RandomData.getNowTime;

/**
 * Created by Administrator on 2017/4/27.
 */
@Title("用户信息相关接口")
public class UserResourceTest extends SetBaseServer {

    private String userToken = null;
    private String loginNo = super.loginNo;

    @Before
    public void setUp() throws Exception {
        //userToken = WriteAndReadFile.readFile(loginNo + "_AccessToken.json");
        userToken = getSaveData(loginNo + "accessToken").toString();

    }

    @Title("获取用户的信息")
    @Description("获取用户18606535378的信息，并验证。" +
            "\n 保存用户login信息供后面使用")
    @Test
    public void getUserInfo() throws Exception {

        Response response = given().
                contentType(ContentType.JSON).
                header("Authorization", userToken).
        when().
                get("/jiadao/api/user/personal_info/" + loginNo).
        then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("0")).
                body("msg", equalTo("成功")).
        extract().
                response();

        String loginID = response.getBody().jsonPath().getString("result.login");
        //写入文件
        saveDatas.put(loginNo + "loginID", loginID);
       // WriteAndReadFile.writeFile(loginNo + "_loginID.json", loginID);

    }

    @Title("修改用户昵称接口")
    @Description("修改用户昵称（后面加上时间戳），验证是否成功")
    @Test
    public void changeNickname() throws Exception {

        String json = "{\"nickName\":\"自动化" + getNowTime() + "\"}";

        given().
                contentType(ContentType.JSON).
                header("Authorization", userToken).
                body(json).
        when().
                put("/jiadao/api/user/change_username").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("0")).
                body("msg", equalTo("成功"));


    }

    @Title("修改用户自我介绍接口")
    @Description("修改用户自我介绍（后面加上时间戳），验证是否成功")
    @Test
    public void changeIntro() throws Exception {

        String json = "{\"intro\":\"自动化api修改用户自我介绍" + getNowTime() + "\"}";

        given().
                contentType(ContentType.JSON).
                header("Authorization", userToken).
                body(json).
        when().
                put("/jiadao/api/user/change_intro").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("0")).
                body("msg", equalTo("成功"));

    }

    @Title("修改用户头像接口")
    @Description("修改用户的头像（仅传递图片路径），验证是否成功")
    @Test
    public void changeAvatar() throws Exception {

        String json = "{\"avatar\":\"/test/2017-04-27/149327971569493.jpg\"}";

        given().
                contentType(ContentType.JSON).
                header("Authorization", userToken).
                body(json).
        when().
                put("/jiadao/api/user/change_avatar").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code", equalTo("0")).
                body("msg", equalTo("成功"));

    }


}
