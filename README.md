#  这里有个坑使用jpa时一定注意jpa规范


|    keyword    | sample | Elasticsearch Query String|
| ---------- | --- |---|
| And |  findByNameAndPrice |{"bool" : {"must" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}|
| Or       |  findByNameOrPrice |{"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}|
|Is   |findByName | {"bool" : {"must" : {"field" : {"name" : "?"}}}}|
|Not  | findByNameNot | {"bool" : {"must_not" : {"field" : {"name" : "?"}}}}|
|Between | findByPriceBetween | {"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}|
|LessThanEqual | findByPriceLessThan | {"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}|
|GreaterThanEqual | findByPriceGreaterThan | {"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}|
|Before |  findByPriceBefore | {"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}|
|After | findByPriceAfter| {"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : null,"include_lower" : true,"include_upper" : true}}}}}|
|Like | findByNameLike | {"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}} |
|StartingWith |findByNameStartingWith | {"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}}|
|EndingWith | findByNameEndingWith |{"bool" : {"must" : {"field" : {"name" : {"query" : "*?","analyze_wildcard" : true}}}}}|
|Contains/Containing | findByNameContaining| {"bool" : {"must" : {"field" : {"name" : {"query" : "?","analyze_wildcard" : true}}}}}|
|In | findByNameIn(Collection<String>names) |{"bool" : {"must" : {"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"name" : "?"}} ]}}}}|
|NotIn | findByNameNotIn(Collection<String>names)| {"bool" : {"must_not" : {"bool" : {"should" : {"field" : {"name" : "?"}}}}}}|
|Near |findByStoreNear | Not Supported Yet !|
|True |findByAvailableTrue |{"bool" : {"must" : {"field" : {"available" : true}}}}|
|False |findByAvailableFalse |{"bool" : {"must" : {"field" : {"available" : false}}}}|
|OrderBy | findByAvailableTrueOrderByNameDesc|{"sort" : [{ "name" : {"order" : "desc"} }],"bool" : {"must" : {"field" : {"available" : true}}}}|



#  Hibernate Validator常用注解
|    注解    | 解释 | 
| ---------- | --- |
| @Null | 必须为空|
|@NotNull| 不能为空|
|@AssertTrue|必须为true|
|@AssertFalse|必须为false|
|@Min|必须为数字，其值大于或等于指定的最小值|
|@Max|必须为数字，其值小于或等于指定的最大值|
|@DecimalMin|必须为数字，其值大于或等于指定的最小值|
|@DecimalMax|必须为数字，其值小于或等于指定的最大值|
|@Size|集合的长度|
|@Digits|必须为数字，其值必须再可接受的范围内|
|@Past|必须是过去的日期|
|@Future|必须是将来的日期|
|@Pattern|必须符合正则表达式|
|@Email|必须是邮箱格式|
|@Length|长度范围|
|@NotEmpty|不能为null，长度大于0|
|@Range|元素的大小范围|
|@NotBlank|不能为null，字符串长度大于0(限字符串)|