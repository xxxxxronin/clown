#Clown LightDB
===================
简易的Mybaits数据操作包,目前只支持MySQL数据库操作,使用阿里开源的Druid为数据源连接
###Maven引用方式
```xml
    <dependency>
        <groupId>com.clown.lightdb</groupId>
        <artifactId>ms-lightdb</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```
###配置说明 (配置文件为Maven 资源目录下config.properties )
<table>
<tbody>
<tr>
<th>配置项</th>
<th>说明</th>
</tr>
<tr>
<td>ms.mybatis.basepackage</td>
<td>mybatis mapper扫描包名,可多个用逗号分隔如 com.clown.dao,com.apache.dao</td>
</tr>

<tr>
<td>ms.db.url</td>
<td>数据据连接字符串如:jdbc:mysql://vm-server5:3306/missionsky?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;allowMultiQueries=true</td>
</tr>

<tr>
<td>ms.db.username</td>
<td>数据库用户名</td>
</tr>

<tr>
<td>ms.db.password</td>
<td>数据库密码</td>
</tr>
</tbody>

</table>

###PaginationPlugin 分页插件应用 示例如下:
Mapper XML
```xml
 <select id="findUserModel" resultType="com.alka.example.models.UserModel">
         SELECT  * FROM  users;
 </select>
```
Java 接口
```java
    public Pagination<UserModel> findUserModel(PageBounds pageBounds) throws Exception;
```
调用方式
```java
    Pagination<UserModel> modelPagination= userDao.findUserModel(new PageBounds(2,5));
```
PageBounds(2,5) 第一个参数为起始页码,第二个参数为显示数量
###Mapper CURD 操作方法
对实体对象进行简单的增删查改
以下面实体为例
```java
        UserModel userModel = new UserModel();
        userModel.setUserName("jimmy");
        userModel.setEmail("63253@admin.com");
        userModel.setQq("000000000000");
        userModel.setUserNick("杨林");
```
####新增示例
```java
     Mapper.insert(userModel).exec();
```

###删除示例
```java
    Mapper.delete(UserModel.class).where(new Condition("id",17)).exec();
```
###更新示例
```java
    Mapper.update(userModel).where(new Condition("id",17)).exec();
```

###查询示例
```java
    userModel= Mapper.select(UserModel.class).where(new Condition("user_name","karl")).findOne(UserModel.class);
```