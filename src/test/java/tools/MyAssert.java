package tools;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by wwl on 2017/5/2.
 */
public class MyAssert extends Assertions{
    Response response;

    @Step("验证是否包含\"{0}\"")
    public static void assertMatch(Response response,String jsonPath ,String expected) {
        assertThat(APITools.getJsonPathValue(response,jsonPath)).isEqualTo(expected);

    }

    @Step("验证HTTP状态码是否正确")
    public static void assertHttpStatus(int httpStatus) {
        assertThat(httpStatus).isEqualTo(HttpStatus.SC_OK);
    }
}
