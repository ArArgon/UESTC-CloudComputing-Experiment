# UESTC 云计算 Hadoop 实验代码

**IDEA 版**

---

**注意**: 项目中使用的 `hadoop` 版本均为 `2.10.1`, 服务器端版本也相同. 若要与实验指导书配置一致， 请修改 `pom.xml` 中的版本号.

## 文件说明

- `src/main/resources/log4j.properties`: `log4j` 配置文件
- `pom.xml`: `maven` 依赖文件

### 词频统计实验
- `src/main/java/WordCount.java`: 词频统计实验代码

### 反向检索实验
- `src/main/java/InverseIndex.java`: 反向检索实验主代码
- `src/main/java/SetWritable.java`: 反向检索实验所需可写集合代码

## IntelliJ IDEA 环境配置

~~抵制 eclipse 从我做起~~

1. 按照实验指导书，在远程主机（虚拟机）搭建 `Hadoop` 环境. 
2. 在远程主机上启动 Hadoop 服务，并确认允许 SSH 连接.
3. 配置 IDEA 远程调试环境，对于每个可运行配置 (Configuration)，将 `Run On` 的 Target 设置为远程主机.
4. Build and run 中，将 Java 环境选择为远程端.
5. 修改 `CLI`(命令行) 参数，形如
    ```
    hdfs://localhost:9000/user/zhangsan/input hdfs://localhost:9000/user/zhangsan/output
    ```
6. 运行 Maven, 下载依赖包(?)
7. 运行/调试代码
   - **注意**: 每次运行时，请**务必删除**之前产生的输出 `output` 文件夹，否则会造成异常.