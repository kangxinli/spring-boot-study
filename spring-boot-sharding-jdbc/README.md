

## 一、运行步骤

### 1. 修改application.yml配置

* 修改`spring.profiles.active`的值为需要运行的场景
  1. `master-slave`：一主多从模式下的读写分离
  2. `sharding-databases`：使用取模的方式来实现库分片
  3. `sharding-databases2`：使用固定值的方式来实现库分片
  4. `sharding-databases3`：工程里既有分片的表，也有不分片的表(使用默认的库)
  5. `sharding-tables`：使用取模的方式来实现表分片
* 修改数据库的配置

### 2. 初始化数据

执行`sharding-jdbc-demo.sql`脚本

### 3. 启动工程

运行`ShardingApplication`

&nbsp;

## 三、测试接口

### 1. 初始化数据
生成10条用户数据

http://localhost:11001/init

### 2. 查询所有用户数据
http://localhost:11001/

### 3. 查询某个用户数据

http://localhost:11001/{id}

### 4. 清除所有数据

http://localhost:11001/clean