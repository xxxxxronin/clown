Clown Framework
==========================================
Clown Framework 是一个简易的基于Spring MVC的Web框架,将常规的配置灵活固化，框架中集成了spring
mvc + Freemark Tempalte + Apache Shiro

###### Maven 使用配置说明
```
     <dependency>
        <groupId>com.clown.framework</groupId>
        <artifactId>clown-framework</artifactId>
        <version>1.1-SNAPSHOT</version>
     </dependency>
```

###### web.xml 配置说明(如果web启动容器支持javax.servlet 3.0+可以省别略以下配置)
```
  <listener>
         <listener-class>com.clown.framework.listeners.FrameworkLoadListeners</listener-class>
  </listener>
```

###### config.properties文件说明
请参考clown-orm配置说明

###### webapps目录说明(webapps是WEB-INF根目录)
<table>
<tr>
<td>目录名称</td>
<td>说明</td>
</tr>

<tr>
<td>template</td>
<td>freemark html视图目录</td>
</tr>

<tr>
<td>static</td>
<td>静态资源目录(存放js、css等资源文件)</td>
</tr>
</table>