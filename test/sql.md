clickhosue 对日期时间处理函数的支持，相对于mysql

少部分支持，大多数不支持。

可修改部分函数为clickhouse对应函数，剩余函数在click'hosue中没有支持。

⭕

| mysql函数         | clickhouse 测试 | 修改方案                                                     | 修改后测试 |
| :---------------- | --------------- | :----------------------------------------------------------- | ---------- |
| adddate           | ❌               | addDays,addYears, addMonths, addWeeks, addDays, <br>addHours, addMinutes, addSeconds,addQuarters  根据实际情况修改 | ✔️          |
| adtime            | ❌               |                                                              |            |
| curdate           | ❌               | today                                                        | ✔️          |
| current_date      | ❌               | today                                                        | ✔️          |
| current_time      | ❌               |                                                              |            |
| current_timestamp | ❌               |                                                              |            |
| curtime           | ❌               |                                                              |            |
| date              | ✔️               |                                                              |            |
| datediff          | ❌               | dateDiff                                                     | ✔️          |
| date_add          | ❌               |                                                              |            |
| date_format       | ❌               | formatDateTime                                               | ✔️          |
| date_sub          | ❌               | subtractYears,subtractMonths,subtractWeeks,<br/>subtractDays,subtractours,subtractMinutes,<br/>subtractSeconds,subtractQuarters 根据实际情况修改 | ✔️          |
| day               | ✔️               |                                                              |            |
| dayname           | ❌               |                                                              |            |
| dayofmonth        | ❌               | toDayOfMonth                                                 | ✔️          |
| dayofweek         | ❌               | toDayOfWeek                                                  | ✔️          |
| dayofyear         | ❌               | toDayOfYear                                                  | ✔️          |
| extract           | ✔️               | 支持，内部会转换为clickhouse对应的toXX                       | ✔️          |
| from_days         | ❌               |                                                              |            |
| hour              | ✔️               |                                                              |            |
| last_day          | ❌               |                                                              |            |
| localtime         | ❌               |                                                              |            |
| localtimestamp    | ❌               |                                                              |            |
| makedate          | ❌               |                                                              |            |
| maketime          | ❌               |                                                              |            |
| microsecond       | ❌               |                                                              |            |
| minute            | ✔️               |                                                              |            |
| monthname         | ❌               |                                                              |            |
| month             | ✔️               |                                                              |            |
| now               | ✔️               |                                                              |            |
| period_add        | ❌               |                                                              |            |
| period_diff       | ❌               |                                                              |            |
| quarter           | ✔️               |                                                              |            |
| second            | ✔️               |                                                              |            |
| sec_to_time       | ❌               |                                                              |            |
| str_to_date       | ❌               |                                                              |            |
| subdate           | ❌               |                                                              |            |
| subtime           | ❌               |                                                              |            |
| sysdate           | ❌               |                                                              |            |
| time              | ❌               |                                                              |            |
| time_format       | ❌               | formatDateTime                                               | ✔️          |
| time_to_sec       | ❌               |                                                              |            |
| timediff          | ❌               | dateDiff                                                     | ✔️          |
| timestamp         | ❌               |                                                              |            |
| to_days           | ❌               |                                                              |            |
| week              | ✔️               |                                                              |            |
| weekday           | ❌               | toDayOfWeek                                                  | ✔️          |
| weekofyear        | ❌               |                                                              |            |
| year              | ✔️               |                                                              |            |
| yearweek          | ❌               |                                                              |            |
| unix_timestamp    | ❌               | toUnixTimestamp                                              | ✔️          |
| from_unixtime     | ❌               |                                                              |            |





