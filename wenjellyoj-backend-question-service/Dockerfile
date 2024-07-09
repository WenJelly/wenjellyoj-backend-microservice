# 基础镜像
FROM openjdk:8-jdk-alpine

# 指定工作目录
WORKDIR /app

# 拷贝jar包到工作目录
ADD target/wenjellyoj-backend-question-service-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8081

# 启动命令
ENTRYPOINT ["java","-jar","/app/wenjellyoj-backend-question-service-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
