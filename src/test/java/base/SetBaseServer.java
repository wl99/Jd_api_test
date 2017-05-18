package base;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class SetBaseServer {

    /**
     * 公共参数数据池（全局可用）
     */
    protected static Map<String, Object> saveDatas = new HashMap<>();

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(15080);
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

    public String loginNo = "18606535378";
    /**
     * 存入公共参数
     */
    protected void setSaveDates(Map<String, Object> map) {
        saveDatas.putAll(map);
    }

    /**
     * 获取公共数据池中的数据
     * @param key 公共数据的key
     * @return 对应的value
     */
    protected Object getSaveData(String key) {
        if ("".equals(key) || !saveDatas.containsKey(key)) {
            return null;
        } else {
            return saveDatas.get(key);
        }
    }







}
