# UESTC 云计算 Hadoop 实验代码

---

## 文件说明

- `src/main/java/WordCount.java`: 词频统计实验代码
- `src/main/java/InverseIndex.java`: 反向检索实验代码
- `src/main/resources/log4j.properties`: `log4j` 配置文件

## 环境配置

1. 按照实验指导书，在远程主机（虚拟机）搭建 `Hadoop` 环境. 
2. 在远程主机上启动 Hadoop 服务，并确认允许 SSH 连接.
3. 配置 IDEA 远程调试环境，对于每个可运行配置 (Configuration)，将 `Run On` 的 Target 设置为远程主机.
4. Build and run 中，将 Java 环境选择为远程端.
5. 运行 Maven, 下载依赖包(?)
6. 运行/调试代码