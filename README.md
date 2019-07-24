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