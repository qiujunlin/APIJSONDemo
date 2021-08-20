mysql中所有有关json的函数在clickhouse中都不支持。项目中应尽量少使用json相关的函数。ClickHouse中有自己的函数。

```
SQL_FUNCTION_MAP.put("json_append", "");  // JSON_APPEND(json_doc, path, val[, path, val] ...)) 插入JSON数组
SQL_FUNCTION_MAP.put("json_array", "");  // JSON_ARRAY(val1, val2...) 创建JSON数组
SQL_FUNCTION_MAP.put("json_array_append", "");  // JSON_ARRAY_APPEND(json_doc, val) 将数据附加到JSON文档
SQL_FUNCTION_MAP.put("json_array_insert", "");  // JSON_ARRAY_INSERT(json_doc, val) 插入JSON数组
SQL_FUNCTION_MAP.put("json_contains", "");  // JSON_CONTAINS(json_doc, val) JSON文档是否在路径中包含特定对象
SQL_FUNCTION_MAP.put("json_contains_path", "");  // JSON_CONTAINS_PATH(json_doc, path) JSON文档是否在路径中包含任何数据
SQL_FUNCTION_MAP.put("json_depth", "");  // JSON_DEPTH(json_doc) JSON文档的最大深度
SQL_FUNCTION_MAP.put("json_extract", "");  // JSON_EXTRACT(json_doc, path) 从JSON文档返回数据
SQL_FUNCTION_MAP.put("json_insert", "");  // JSON_INSERT(json_doc, val) 将数据插入JSON文档
SQL_FUNCTION_MAP.put("json_keys", "");  // JSON_KEYS(json_doc[, path]) JSON文档中的键数组
SQL_FUNCTION_MAP.put("json_length", "");  // JSON_LENGTH(json_doc) JSON文档中的元素数
SQL_FUNCTION_MAP.put("json_merge", "");  // JSON_MERGE(json_doc1, json_doc2) （已弃用） 合并JSON文档，保留重复的键。JSON_MERGE_PRESERVE（）的已弃用同义词
SQL_FUNCTION_MAP.put("json_merge_patch", "");  // JSON_MERGE_PATCH(json_doc1, json_doc2) 合并JSON文档，替换重复键的值
SQL_FUNCTION_MAP.put("json_merge_preserve", "");  // JSON_MERGE_PRESERVE(json_doc1, json_doc2) 合并JSON文档，保留重复的键
SQL_FUNCTION_MAP.put("json_object", "");  // JSON_OBJECT(key1, val1, key2, val2...) 创建JSON对象
SQL_FUNCTION_MAP.put("json_overlaps", "");  // JSON_OVERLAPS(json_doc1, json_doc2) （引入8.0.17） 比较两个JSON文档，如果它们具有相同的键值对或数组元素，则返回TRUE（1），否则返回FALSE（0）
SQL_FUNCTION_MAP.put("json_pretty", "");  // JSON_PRETTY(json_doc) 以易于阅读的格式打印JSON文档
SQL_FUNCTION_MAP.put("json_quote", "");  // JSON_QUOTE(json_doc1) 引用JSON文档
SQL_FUNCTION_MAP.put("json_remove", "");  // JSON_REMOVE(json_doc1, path) 从JSON文档中删除数据
SQL_FUNCTION_MAP.put("json_replace", "");  // JSON_REPLACE(json_doc1, val1, val2) 替换JSON文档中的值
SQL_FUNCTION_MAP.put("json_schema_valid", "");  // JSON_SCHEMA_VALID(json_doc) （引入8.0.17） 根据JSON模式验证JSON文档；如果文档针对架构进行验证，则返回TRUE / 1；否则，则返回FALSE / 0
SQL_FUNCTION_MAP.put("json_schema_validation_report", "");  // JSON_SCHEMA_VALIDATION_REPORT(json_doc, mode) （引入8.0.17） 根据JSON模式验证JSON文档；以JSON格式返回有关验证结果的报告，包括成功或失败以及失败原因
SQL_FUNCTION_MAP.put("json_search", "");  // JSON_SEARCH(json_doc, val) JSON文档中值的路径
SQL_FUNCTION_MAP.put("json_set", "");  // JSON_SET(json_doc, val) 将数据插入JSON文档
//    SQL_FUNCTION_MAP.put("json_storage_free", "");  // JSON_STORAGE_FREE() 部分更新后，JSON列值的二进制表示形式中的可用空间
//    SQL_FUNCTION_MAP.put("json_storage_size", "");  // JSON_STORAGE_SIZE() 用于存储JSON文档的二进制表示的空间
SQL_FUNCTION_MAP.put("json_table", "");  // JSON_TABLE() 从JSON表达式返回数据作为关系表
SQL_FUNCTION_MAP.put("json_type", "");  // JSON_TYPE(json_doc) JSON值类型
SQL_FUNCTION_MAP.put("json_unquote", "");  // JSON_UNQUOTE(json_doc) 取消引用JSON值
SQL_FUNCTION_MAP.put("json_valid", "");  // JSON_VALID(json_doc) JSON值是否有效
SQL_FUNCTION_MAP.put("json_arrayagg", "");  // JSON_ARRAYAGG(key) 将每个表达式转换为 JSON 值，然后返回一个包含这些 JSON 值的 JSON 数组
SQL_FUNCTION_MAP.put("json_objectagg", "");  // JSON_OBJECTAGG(key, val))  将每个表达式转换为 JS4ON 值，然后返回一个包含这些 JSON 值的 JSON 对象
```

查询clickhosue与json相关的函数：

```
select name  from system.functions where name like '%JSON%'
```

查询结果，结果发现，几乎所有的mysql里的json函数  clickhouse都不支持。

```
─name────────────────────────┐
│ toJSONString                │
│ JSONExtractRaw              │
│ JSONExtractString           │
│ JSONExtractFloat            │
│ JSONKey                     │
│ simpleJSONExtractBool       │
│ simpleJSONHas               │
│ JSONExtractArrayRaw         │
│ JSONExtractKeysAndValuesRaw │
│ JSONHas                     │
│ JSONExtractBool             │
│ JSONType                    │
│ JSONExtractKeysAndValues    │
│ isValidJSON                 │
│ JSONExtractInt              │
│ JSONLength                  │
│ JSONExtractUInt             │
│ simpleJSONExtractFloat      │
│ JSONExtract                 │
│ simpleJSONExtractUInt       │
│ simpleJSONExtractInt        │
│ simpleJSONExtractRaw        │
│ simpleJSONExtractString 
```



```

select `praiseUserIdList` from `test`.`Moment` limit 10;

select JSONLength(`praiseUserIdList`) from `test`.`Moment` limit 10;

```

```
SQL_FUNCTION_MAP.put("visitParamHas", ""); //visitParamHas(params, name)检查是否存在«name»名称的字段
SQL_FUNCTION_MAP.put("visitParamExtractUInt", ""); //visitParamExtractUInt(params, name)将名为«name»的字段的值解析成UInt64。
SQL_FUNCTION_MAP.put("visitParamExtractInt", ""); //与visitParamExtractUInt相同，但返回Int64。
SQL_FUNCTION_MAP.put("visitParamExtractFloat", ""); //与visitParamExtractUInt相同，但返回Float64。
SQL_FUNCTION_MAP.put("visitParamExtractBool", "");//解析true/false值。其结果是UInt8类型的。
SQL_FUNCTION_MAP.put("visitParamExtractRaw", ""); //返回字段的值，包含空格符。
SQL_FUNCTION_MAP.put("visitParamExtractString", ""); //使用双引号解析字符串。这个值没有进行转义。如果转义失败，它将返回一个空白字符串。
SQL_FUNCTION_MAP.put("JSONHas", ""); //如果JSON中存在该值，则返回1。
SQL_FUNCTION_MAP.put("JSONLength", ""); //返回JSON数组或JSON对象的长度。
SQL_FUNCTION_MAP.put("JSONType", ""); //返回JSON值的类型。
SQL_FUNCTION_MAP.put("JSONExtractUInt", ""); //解析JSON并提取值。这些函数类似于visitParam*函数。
SQL_FUNCTION_MAP.put("JSONExtractInt", ""); //
SQL_FUNCTION_MAP.put("JSONExtractFloat", ""); //
SQL_FUNCTION_MAP.put("JSONExtractBool", ""); //
SQL_FUNCTION_MAP.put("JSONExtractString", ""); //解析JSON并提取字符串。此函数类似于visitParamExtractString函数。
SQL_FUNCTION_MAP.put("JSONExtract", "");//解析JSON并提取给定ClickHouse数据类型的值。
SQL_FUNCTION_MAP.put("JSONExtractKeysAndValues", ""); //从JSON中解析键值对，其中值是给定的ClickHouse数据类型
SQL_FUNCTION_MAP.put("JSONExtractRaw", ""); //返回JSON的部分。
SQL_FUNCTION_MAP.put("toJSONString", ""); //将值序列化为其JSON表示形式。
SQL_FUNCTION_MAP.put("abs", ""); //
SQL_FUNCTION_MAP.put("abs", ""); //
SQL_FUNCTION_MAP.put("abs", ""); //
```

