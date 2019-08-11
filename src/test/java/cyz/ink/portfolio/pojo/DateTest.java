package cyz.ink.portfolio.pojo;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ Author      : Zink
 * @ Date        : Created in 20:31 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
public class DateTest {
    @Test
    public void t(){
        add(null);
    }

    public void add(CurrentPrice bean) {

        //连接对象
        Connection con=null;
        //SQL语句执行对象
        PreparedStatement ps=null;
        // 加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //建立连接,localhost为主机IP地址（本机），3306为mysql的端口号，
            //testdb为数据库的库名,characterEncoding=utf-8为字节编码集
            //root为mysql的登录名，123456为mysql的登录密码jdbc:mysql://localhost:3306/portfolio?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root","root");
            System.out.println(con);

            //执行SQL语句，？为占位符
            ps=con.prepareStatement("insert into currentPrice (date) values(?)");
//            //填充占位符
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(Calendar.getInstance().getTime());
            ps.setString(1, timeStamp);
//            ps.setString(1, bean.getDate());
//            ps.setDate(2, bean.getBirthday());
//            ps.setInt(3, bean.getMoney());
            //更新数据库
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{//关闭连接
            try {
                ps.close();
                con.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
