# lab-back-end
前后端分离的实验室管理系统，基于SpringBoot+Vue

[项目的前端部分](https://github.com/FatShallot/lab-front-end)

后台使用了
- SpringBoot
- SpringSecurity
- MyBatis

# 说明
这是一个前后端分离的项目，内项目本身并不复杂，完全没有必要写成前后端分离。

做成前后端分离只是为了自己练习用，前后端分离会出现很多使用JSP、Thymeleaf时没有碰到的问题。
比如CORS问题，比如前后端的数据交互问题。

遇到的所有问题我都会整理成文档放到项目的documents文件夹下。

# application.yml
为了避免数据库密码泄露，暂时先不提交yml文件了，配置都先写在这里
```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/lab?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false
server:
  port: 10010
mybatis:
  configuration:
    map-underscore-to-camel-case: true
logging:
  level:
    com.lpc.labbackend: debug
```