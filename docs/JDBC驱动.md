测试不同的jdbc“

clickhouse的jdbc驱动一共有三个：

分别是：

### ClickHouse JDBC driver

```
<dependency>
    <groupId>ru.yandex.clickhouse</groupId>
    <artifactId>clickhouse-jdbc</artifactId>
    <version>0.3.1</version>
</dependency>
```

[链接](https://github.com/ClickHouse/clickhouse-jdbc)

### Clickhouse4j

- lighter and faster alternative for the official ClickHouse JDBC driver

[链接](https://github.com/blynkkk/clickhouse4j)

```
<dependency>
    <groupId>cc.blynk.clickhouse</groupId>
    <artifactId>clickhouse4j</artifactId>
    <version>1.4.4</version>
</dependency>
```



### ClickHouse Native JDBC

[链接](https://github.com/housepower/ClickHouse-Native-JDBC)

```
// (recommended) shaded version, available since 2.3-stable
<dependency>
    <groupId>com.github.housepower</groupId>
    <artifactId>clickhouse-native-jdbc-shaded</artifactId>
    <version>${clickhouse-native-jdbc.version}</version>
</dependency>
```



官方驱动提供jdbc驱动有两个，一个是官方驱动，一个是第三方驱动

官方驱动如下

```
<dependency>
    <groupId>ru.yandex.clickhouse</groupId>
    <artifactId>clickhouse-jdbc</artifactId>
    <version>0.3.1</version>
    </exclusions>
</dependency>
```

第三方驱动 是Clickhouse-Nativa-jdbc 和 clickhouse4j

就你提到的这个问题  执行时rsmd.getTableName返回"‘Comment’"

经过验证和测试。

sql语句不变，还是issuse里的。

```
SELECT `Comment`.*, `User`.`id`,`User`.`name` FROM `sys`.`Comment` AS `Comment`  
   INNER JOIN `sys`.`apijson_user` AS `User` ON `User`.`id` = `Comment`.`userId` WHERE  (  (  (match(`Comment`.`content`, 'a'))  )  AND  (  (  (match(`User`.`name`, 'a') OR match(`User`.`name`, 't'))  )  )  )  ORDER BY `Comment`.`date` DESC  LIMIT 10

```

使用clickhouse4j  执行rsmd.getTableName返回"‘Comment’"，有反引号

而使用官方驱动 clickhouse-jdbc 返回的是 "Comment"，没有反引号 

所以这个问题 ，如果在getQuote()中去掉"`"，那么clickhouse生成的sql 语句都没有反引号。

或许这样改是否合适一点，生成的sql保留反引号，在进行判断的时候如果使用的jdbc驱动是clickhouse4j  ，获取的表名是有反引号的。将它截取下来再比较。

```
String sqlTable = rsmd.getTableName(i);
if (config.isClickHouse()&&sqlTable.startWith("`")) {
  sqlTable = sqlTable.substring(1,sqlTable.length()-1);
}
...
} else if ( ! config.getSQLTable() .equalsIgnoreCase(sqlTable)) {

```

因为[clickhosue](https://clickhouse.tech/docs/zh/sql-reference/syntax/
)官方有一句话：

> 关键字不是保留的；它们仅在相应的上下文中才会被认为是关键字。如果你使用和关键字同名的 标识符 ，需要使用双引号或反引号将它们包含起来。例如：如果表 table_name 包含列 "FROM"，那么 SELECT "FROM" FROM table_name 是合法的

说明clickhouse是支持表同名的。并且必须要使用反引号或者双引号。



```
{
    "[]": {
        "join": "&/User/id@",
        "Moment": {
           // "@column": "id,userId,content"
        },
        "User": {
            "name~": [
                "b",
                "j"
            ],
            "@column": "id,name,tag",
            "id@": "/Moment/userId"
        }
    }
}
                                                                                                                                                                                                              
```

