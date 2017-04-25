package base;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Created by Administrator on 2017/4/6.
 */
@Ignore
public class SetBaseServer {

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(18080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }


//        String basePath = System.getProperty("server.base");
//        if(basePath==null){
//            basePath = "/";
//        }
//        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://192.168.0.202";
        }
        RestAssured.baseURI = baseHost;

    }

}
