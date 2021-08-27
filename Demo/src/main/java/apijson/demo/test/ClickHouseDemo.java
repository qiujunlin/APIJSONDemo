package apijson.demo.test;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ClickHouseDemo {

        public static void main(String[] args) {
            String sqlDB = "show databases";//查询数据库
            String sqlTab = "show tables";//查看表
            String sqlCount = "select count(*) count from ";//查询ontime数据量
            String sql = "SELECT `id`, `name`,`tag` FROM `test`.`apijson_user` limit 2";
            String a = "select * from test.apijson_user where id=38710";
            String b = "select * from \"test\".\"apijson_user\" where id=38710";
            String select = "SELECT `Comment`.*, `User`.`id`,`User`.`name` FROM `test`.`Comment` AS `Comment`  \n" +
                    "   INNER JOIN `test`.`apijson_user` AS `User` ON `User`.`id` = `Comment`.`userId` WHERE  (  (  (match(`Comment`.`content`, 'a'))  )  AND  (  (  (match(`User`.`name`, 'a') OR match(`User`.`name`, 't'))  )  )  )  ORDER BY `Comment`.`date` DESC  LIMIT 10\n";//查询ontime数据量
            String test1  ="SELECT `Moment`.*, `User`.`id`,`User`.`name`,`User`.`tag` FROM `test`.`Moment` AS `Moment`  \n" +
                    "   INNER JOIN `test`.`apijson_user` AS `User` ON `User`.`id` = `Moment`.`userId` WHERE  (  (  (  (match(`User`.`name`, 'b') OR match(`User`.`name`, 'j'))  )  )  )  LIMIT 10\n";
            // exeSql(sqlDB);
            //exeSql(sqlTab);qiujunlin
           // exeSql(a);
            String sql2 = "SELECT splitByChar('i',`content`) FROM `test`.`Comment` LIMIT 10";
            sql2 = "SELECT lcase(F%DSFS),concat(`name`,`head`) FROM `test`.`apijson_user` LIMIT 10";
//            exeSql(sql);
//            exeSql(b);
         //   exeSql(select);
            exeSql(sql2);
        }

        public static void exeSql(String sql){
            String address = "jdbc:clickhouse://192.168.108.128:8123";
            Connection connection = null;
            Statement statement = null;
            ResultSet results = null;
            try {
                Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
//                Class.forName("cc.blynk.clickhouse.ClickHouseDriver");
                connection = DriverManager.getConnection(address,"default","123456");
                statement = connection.createStatement();
                long begin = System.currentTimeMillis();
                results = statement.executeQuery(sql);
                long end = System.currentTimeMillis();
                System.out.println("执行（"+sql+"）耗时："+(end-begin)+"ms");
                ResultSetMetaData rsmd = results.getMetaData();
                List<Map> list = new ArrayList();
                int idnex  = 0;
                while(results.next()){
                    Map map = new HashMap();
                    for(int i = 1;i<=rsmd.getColumnCount();i++){
                        map.put(rsmd.getColumnName(i),results.getString(rsmd.getColumnName(i)));
                    }
                    System.out.println(rsmd.getTableName(idnex++));
                    list.add(map);
                }
                for(Map map : list){
                    System.err.println(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {//关闭连接
                try {
                    if(results!=null){
                        results.close();
                    }
                    if(statement!=null){
                        statement.close();
                    }
                    if(connection!=null){
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



}
