package tools;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * Created by Administrator on 2017/4/28.
 */
public class APITools {


    /**
     * 免认证的Post方法请求
     *
     * @param apiPath
     * @param json
     * @return 请求响应
     */
    @Step("发起Post请求")
    public static Response post(String apiPath, String json) {
        Response response = given().contentType(ContentType.JSON).body(json).
                when().post(apiPath.trim());
        return response;
    }

    /**
     * 免认证的Get请求
     *
     * @param apiPath
     * @return
     */
    @Step("发起Get请求")
    public static Response get(String apiPath) {
        Response response = given().contentType(ContentType.JSON).
                when().get(apiPath.trim());

        return response;
    }

    /**
     * 带json的需登录后使用的Post请求
     *
     * @param apiPath
     * @param json
     * @param token
     * @return
     */

    @Step
    public static Response post(String apiPath, String json, String token) {
        Response response = given().contentType(ContentType.JSON).header("Authorization", token).body(json).
                when().post(apiPath.trim());
        return response;
    }

    /**
     * 需登录后使用的get请求
     *
     * @param apiPath
     * @param token
     * @return
     */
    @Step
    public static Response get(String apiPath, String token) {
        Response response = given().contentType(ContentType.JSON).header("Authorization", token).
                when().get(apiPath.trim());
        return response;
    }

    /**
     * 带json数据的需登录后操作的put请求
     *
     * @param apiPath
     * @param json
     * @param token
     * @return
     */
    @Step
    public static Response put(String apiPath, String json, String token) {
        Response response = given().contentType(ContentType.JSON).header("Authorization", token).body(json).
                when().put(apiPath.trim());
        return response;
    }

    /**
     * 需登录后操作的delete请求
     *
     * @param apiPath
     * @param token
     * @return
     */
    public static Response del(String apiPath, String token) {
        Response response = given().contentType(ContentType.JSON).header("Authorization", token).
                when().delete(apiPath.trim());
        return response;
    }


    /**
     * 通过Json获取响应中的对应键内容
     *
     * @param response
     * @param jsonPath
     * @return
     */
    public static String getJsonPathValue(Response response, String jsonPath) {
        String responseJson = response.getBody().jsonPath().getString(jsonPath).toString();
        return responseJson;
    }

    /**
     * 通过Json获取响应中对应的LIST内容
     * @param response
     * @param jsonPath
     * @return
     */
    public static List getJsonPathList(Response response, String jsonPath) {
        List responseList = response.getBody().jsonPath().getList(jsonPath);
        return responseList;
    }

    public static int getHttpCode(Response response) {
        int respStatusCode = response.getStatusCode();
        return respStatusCode;
    }
}
