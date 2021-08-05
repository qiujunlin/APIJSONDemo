package apijson.demo;

import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.settings.ClickHouseProperties;
import ru.yandex.clickhouse.settings.ClickHouseQueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClickHouseDemo {
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:clickhouse://192.168.108.128:8123/test";
        ClickHouseProperties properties = new ClickHouseProperties();
// set connection options - see more defined in ClickHouseConnectionSettings
        properties.setClientName("Agent #1");
// set default request options - more in ClickHouseQueryParam
        //    properties.setSessionId("default-session-id");
        properties.setUser("default");
        properties.setPassword("123456");
        ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
        String sql = "select * from apijson_user";
        Map<ClickHouseQueryParam, String> additionalDBParams = new HashMap<>();
// set request options, which will override the default ones in ClickHouseProperties
        additionalDBParams.put(ClickHouseQueryParam.SESSION_ID, "new-session-id");
        try{ ClickHouseConnection conn = dataSource.getConnection();
             ClickHouseStatement stmt = conn.createStatement();
            stmt.executeQuery(sql, additionalDBParams) ;
            ResultSet rs = stmt.getResultSet();
            System.out.println(rs.getMetaData().getTableName(0));
            System.out.println();
        }catch(Exception e){
        }


    }
}
