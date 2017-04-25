package base;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Ignore;

/**
 * Created by Administrator on 2017/4/18.
 */
@Ignore
public class MongodbBaseTest {
    public static void main(String args[]) {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("192.168.0.202", 27018);

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("jiadao");
            System.out.println("Connect to database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
