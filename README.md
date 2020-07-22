# 海绵宝宝个人安全云盘系统环境搭建

**下面的配置要依次配置成功，不能跳着配**

> java环境  JDK 1.8   java开发环境

**下载**

| Windows x64 | 211.54 MB | [jdk-8u251-windows-x64.exe](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html#license-lightbox) |
| ----------- | --------- | ------------------------------------------------------------ |
|             |           |                                                              |

**配置**

https://www.jianshu.com/p/169bc950316b



---

> 集成开发环境  Idea

https://www.jetbrains.com/idea/

---

> Maven   项目管理工具

**下载：**

| Binary zip archive | [apache-maven-3.6.3-bin.zip](https://mirror.bit.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip) |
| ------------------ | ------------------------------------------------------------ |
|                    |                                                              |

**配置**

https://www.jianshu.com/p/62a76daf5096

注意配置本地仓库的位置

注意配置maven阿里镜像

https://blog.csdn.net/weixin_34850743/article/details/99441245

注意idea配置maven

https://jingyan.baidu.com/article/a3a3f811cd5f0b8da2eb8abf.html

---



> 安装JPBC密码库

| JPBC | 2.0.0 | Stable | *[Download](https://sourceforge.net/projects/jpbc/files/jpbc_2_0_0/)* |
| ---- | ----- | ------ | ------------------------------------------------------------ |
|      |       |        |                                                              |

解压后进入jars文件夹看到里面由很多jar包

需要将其中的两个jar包安装到本地仓库

jpbc-api-2.0.0.jar

安装信息：

```xml
<groupId>it.unisa.dia.gas</groupId>
<artifactId>jpbc-api</artifactId>
<version>2.0.0</version>
```



jpbc-plaf-2.0.0.jar

安装信息：

```xml
<groupId>it.unisa.dia.gas</groupId>
<artifactId>jpbc-plaf</artifactId>
<version>2.0.0</version>
```



安装方法：

1. 打开一个终端

2. 运行命令

   ```cmd
   mvn install:install-file -Dfile=你jar包的路径 -DgroupId=你jar包的groupId -DartifactId=你jar包的artifactId -Dversion=你jar包的版本号 -Dpackaging=jar
   ```

   其中groupid、artifactId、版本号前面已给出.

   例如：

   jpbc-api-2.0.0.jar：假设我的这个包的路径为：E:\jpbc\jars\jpbc-api-2.0.0.jar,那么我需要运行以下命令：

   ```cmd
   mvn install:install-file -Dfile=E:\jpbc\jars\jpbc-api-2.0.0.jar -DgroupId=it.unisa.dia.gas -DartifactId=jpbc-api -Dversion=2.0.0 -Dpackaging=jar
   ```

   jpbc-plaf-2.0.0.jar：假设我的这个包的路径为：E:\jpbc\jars\jpbc-plaf-2.0.0.jar，那么我需要运行以下命令：

   ```cmd
   mvn install:install-file -Dfile=E:\jpbc\jars\jpbc-plaf-2.0.0.jar -DgroupId=it.unisa.dia.gas -DartifactId=jpbc-plaf -Dversion=2.0.0 -Dpackaging=jar
   ```

   上面的例子只需要把路径改一下就行了

---

> 下载安装我开发的密码工具包

**位置：**

https://github.com/codemonkey2019/cryptotool

1. 点击下载ZIP包到本地。网页右上方绿色按钮

2. 解压包

3. 进入解压目录根目录

4. 在这个文件夹内打开终端（shift+右建，选在这里打开终端）

5. 运行maven的命令安装到maven仓库

   ```cmd
   mvn install
   ```

---

> MySQL数据库

https://www.cnblogs.com/zaid/p/MySQL.html

# 其他

## 1、本项目代码地址

服务器端：https://github.com/codemonkey2019/-Server

客户端：https://github.com/codemonkey2019/-Client

## 2、项目启动

1. 创建数据库表

   1. 创建一个数据库,并切换到这个数据库

      ```mysql
      create database clouddisk;
      use clouddisk;
      ```

   2. 创建一个用户表

      ```mysql
      create table tb_user(
          id INT(5) PRIMARY KEY AUTO_INCREMENT,
          user_name VARCHAR(255) NOT NULL,
          password VARCHAR(255) NOT NULL
      );
          
      ```

2. 修改项目中的数据库连接配置

   找到并打开： 项目根目录/src/main/resources/application.yml

   修改如下配置

   ```yaml
   spring:
     datasource:
       username: root
       password: 你的密码
       url: jdbc:mysql:///clouddisk?serverTimezone=Asia/Shanghai
       driver-class-name: com.mysql.cj.jdbc.Driver    
       
       #驱动配置根据你使用的驱动自行配置（mysql5和mysql8的驱动路径不同）
       #mysql 5    : com.mysql.jdbc.Driver
       #mysql 8    : com.mysql.cj.jdbc.Driver
   ```

3. 找到主类，启动项目

