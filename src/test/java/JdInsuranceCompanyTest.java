
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;
import base.SetBaseServer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by Administrator on 2017/4/27.
 */
@Title("查询保险公司接口（免认证接口）")
public class JdInsuranceCompanyTest extends SetBaseServer {

    @Title("查询所有保险公司")
    @Description("查询结果显示所有保险公司，并按首字母排序\n" +
            "验证M开头的保险公司包含‘民生保险’和‘美亚保险’")
    @Test
    public void getAllInsuranceCompany() throws Exception {

        given().
                contentType(ContentType.JSON).
        when().
                get("/jiadao/api/public/company/all").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo("0")).
                body("msg",equalTo("成功")).
                body("result[0].samePrefixLetterCompanies*.name",hasItems("安盛天平","安联财险")).
                body("result.findAll {result -> result.prefixLetter == 'M'}.samePrefixLetterCompanies[0].name",hasItems("民生保险","美亚保险"));
    }

    @Title("查询热门保险公司")
    @Description("查询结果显示所有热门保险公司，验证热门中是否包含“国寿寿险”和“太平洋寿险”")
    @Test
    public void getAllHotInsuranceCompany() throws Exception {

        given().
                contentType(ContentType.JSON).
        when().
                get("/jiadao/api/public/company/hotlist").
        then().
                statusCode(HttpStatus.SC_OK).
                body("code",equalTo("0")).
                body("msg",equalTo("成功")).
                body("result*.name",hasItems("国寿寿险","太平洋寿险"));

    }
}
