package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wwl on 2017/5/10.
 */
public class RandomData {
    /**
     * 获取当前时间以yy/MM/dd HH:mm格式展示
     * @return time
     */
    public static String getNowTime(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
        String time = dateFormat.format(now);
        return time;
    }

    /**
     * 获取当前时间以yyyy/MM/dd HH:mm:ss格式展示
     * @return
     */
    public static String getNowTime2(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(now);
        return time;
    }
}
