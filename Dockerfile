# 基于java镜像创建新镜像
FROM java:8
# 作者
MAINTAINER dengyi
#设置工作目录
VOLUME /tmp
# 加载jar
ADD target/syspermission-1.0.jar app.jar
# 暴露端口
EXPOSE 8080
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","-DPROFILE=online","/app.jar","&"]