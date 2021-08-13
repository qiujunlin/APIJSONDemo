进行测试的Demo项目

📝 内容：SpringBoot+APIJSON 接入 clickhouse数据库

📙 clickhouse官方文档：[点击](https://clickhouse.tech/docs/zh/sql-reference/syntax/)

**已经完成内容：**

👌 在虚拟机中安装ubuntu,并安装clickhouse，使用xshell连接，并把本地mysql中的测试数据全部迁移到了clickhouse中

![11](images/1.png)

👌 使用DBeaver 连接了ClickHouse ，能方便查看数据

![11](images/2.png)



👌 根据官方demo编写了clickhouse的demo，在本地运行项目能够在http://apijson.cn/api/  进行基本的查询。



👌  APIPJSON-ORM 查询源码过了一遍 



### **测试过程：**

#### 1.测试json支持

clickhosue对jjson的支持情况：导入json数据类型变为了string类型  。对json的支持不太友好 

| json_contains 函数 |      |      |
| ------------------ | ---- | ---- |
|                    |      |      |
|                    |      |      |
|                    |      |      |

#### 2.测试 日期函数的支持

对日期函数的支持也不太友好。

见issue1。

 

**遇见的bug：**

1. 解决了click-jdbc jar包版本直接引入与springboot-web包冲突的问题

2. 解决了远程数据库访问权限的问题

3. 解决了linux中 clickhouse数据库表名大小写识别的问题

4. 

   












