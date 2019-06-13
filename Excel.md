# [Excel](https://github.com/Cyanss/excel) ([SpringBoot](https://spring.io/projects/spring-boot/) + [JPA](https://spring.io/projects/spring-data-jpa))
```text
SpringBoot+Jpa实现Excel的导入导出(动态Sql、分页查询、联表join)
```
&emsp;&emsp;最近做了一个项目甲方需求中要求数据的导入导出到Excel文件，Excel的复杂表头和数据格式一直是个头疼的问题，
使用poi或者jexcelapi的话就需要花费大量时间处理表头以及数据格式问题，但是整个项目的开发时间只有一周，对接和联调一周，
那就只能去找快速的“黑科技”了--EasyExcel,虽然EasyExcel只是对poi的再封装，但是EasyExcel中有一个模板写入的功能，
是可以很好的解决复杂表头和一些基本的数据格式问题的。抽了点时间整理了一个demo,也包含一些对Jpa的进阶用法(动态Sql分页查询、联表join等)。
## 开发环境
```text
    环境: windows 10
    编译器: IDEA 2018
    数据库: MySQL 5.6
    JDK: jdk1.8.0_92
    Maven: 3.6
```

## 项目结构树 
```markdown
│  .gitignore
│  LICENSE
│  pom.xml                                                          --项目pom文件
│  README.md                                                        --readme.md文件						
├─github
│  └─image                                                          --用于存放readme.md中链接的图片
├─other
│      Excel.sql                                                    --数据库建表Sql语句
│      Excel数据导入测试[PostMan].postman_collection.json            --信息导入接口PostMan导出的json文件
│      个人信息导入.xlsx                                             --个人信息导入测试数据
│      毕业信息导入.xlsx                                             --毕业信息导入测试数据
│      目录结构树.txt
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─cyan
│  │  │          └─excel
│  │  │              │  ExcelApplication.java                       --SpringBoot启动类
│  │  │              │  
│  │  │              ├─controller
│  │  │              │      ExcelController.java                    --Controller层
│  │  │              │     
│  │  │              ├─entity				
│  │  │              │  │  GraduateInfo.java                        --毕业信息实体
│  │  │              │  │  UserInfo.java                            --用户信息实体
│  │  │              │  │   
│  │  │              │  ├─joint				
│  │  │              │  │      GraduateUserJoint.java               --毕业和用户联表Join实体
│  │  │              │  │      
│  │  │              │  └─model				
│  │  │              │          DetailModel.java                    --详细信息Excel文件对应模型
│  │  │              │          GraduateModel.java                  --毕业信息Excel文件对应模型
│  │  │              │          UserModel.java                      --用户信息Excel文件对应模型
│  │  │              │          
│  │  │              ├─enums				
│  │  │              │      ExcelFileEnum.java                      --Excel文件类型枚举
│  │  │              │      SexTypeEnum.java                        --性别类型枚举
│  │  │              │      
│  │  │              ├─exception				
│  │  │              │  │  ExcelException.java                      --全局异常
│  │  │              │  │  
│  │  │              │  └─handler					
│  │  │              │          ExcelExceptionHandler.java          --全局异常捕获处理
│  │  │              │          
│  │  │              ├─listener					
│  │  │              │      ExcelListener.java                      --excel数据处理
│  │  │              │      
│  │  │              ├─repository
│  │  │              │  │  GraduateInfoRepository.java              --毕业信息DAO层（jpa）
│  │  │              │  │  GraduateUserJointRepository.java	        --用户和毕业联表joinDAO层（jpa）
│  │  │              │  │  UserInfoRepository.java                  --用户信息DAO层（jpa）
│  │  │              │  │  
│  │  │              │  └─Impl
│  │  │              │          GraduateUserJointRepositoryImpl.java--用户和毕业联表joinDAO层（代码实现分页/动态Sql）
│  │  │              │          
│  │  │              ├─result
│  │  │              │      ResponseResult.java                     --返回结果体
│  │  │              │      ResponseResultEnum.java                 --返回结果异常枚举
│  │  │              │      ResponseResultUtils.java                --返回结果工具类
│  │  │              │      
│  │  │              ├─service
│  │  │              │  │  DetailService.java                       --详细信息的Service层
│  │  │              │  │  GraduateService.java                     --毕业信息的Service层
│  │  │              │  │  UserService.java                         --用户信息的Service层
│  │  │              │  │  
│  │  │              │  ├─handler
│  │  │              │  │      ImportHandler.java                   --Excel数据导入处理
│  │  │              │  │      
│  │  │              │  └─Impl
│  │  │              │          DetailServiceImpl.java              --详细信息的Service实现
│  │  │              │          GraduateServiceImpl.java            --毕业信息的Service实现
│  │  │              │          UserServiceImpl.java                --用户信息的Service实现
│  │  │              │          
│  │  │              ├─utils
│  │  │              │      ConvertUtils.java                       --数据转换工具类
│  │  │              │      CopyUtils.java                          --Bean拷贝工具类（不拷贝null值）
│  │  │              │      ExcelUtils.java                         --Excel相关工具类
│  │  │              │      FileUtils.java                          --文件相关工具类
│  │  │              │      PathUtils.java                          --路径相关工具类
│  │  │              │      StreamUtils.java                        --流处理工具类
│  │  │              │      
│  │  │              └─vo
│  │  │                      ImportResultVO.java                    --Excel导入结果VO层
│  │  │                      
│  │  └─resources
│  │      │  application.yml                                        --项目配置文件
│  │      │  	
│  │      ├─static
│  │      │  └─model                                                --Excel导入导出模板
│  │      │          个人信息模板.xlsx						
│  │      │          分类信息模板.xlsx
│  │      │          毕业信息模板.xlsx
│  │      │          详细信息模板.xlsx
```
## Excel模板

### 1、个人信息模板
![个人信息模板 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoModel.png?raw=true)

### 2、分类信息模板
![分类信息模板 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoDistinguishWithSexModel.png?raw=true)

### 3、毕业信息模板
![毕业信息模板 icon](https://github.com/Cyanss/excel/blob/master/github/image/GraduateInfoModel.png?raw=true)

### 4、详细信息模板
![详细信息模板 icon](https://github.com/Cyanss/excel/blob/master/github/image/DetailInfoModel.png?raw=true)

## 项目测试

### 1、导入数据测试

&emsp;&emsp;数据导入时，接收一个formData格式的excel文件对象，参数为excel，后台接收MultipartFile类型，暂时只做了单文件导出，有多文件导入需求的可以去[度娘](https://www.baidu.com)查询。

&emsp;&emsp;Excel的序号一列是不会导入数据库的，UserInfo使用UserId身份证作为主键， GraduateInfo使用自增主键，数据库里两表不设外键关系，只在业务层控制外键关系。设计数据库时只是为了做两表join的功能，把一对一的数据拆分到俩个表里了，导致GraduateInfo不控制的话数据会出现大量重复，因此在Service>handler下做了逻辑上的控制，因此GraduateInfo表参考意义不大。

```java
/** 数据导入信息 返回结果体data */
public class ImportResultVO<T> {
    /** 成功数量 */
    private Integer success = 0;
    /** 失败数量 */
    private Integer failure = 0;
    /** 重复数量 */
    private Integer repeat = 0;
    /** 失败数据 */
    private List<T> failureList;
    /** 重复数据 */
    private List<T> repeatList;  
      
      //...
}
```

#### 1、个人信息导入测试
##### 模拟数据:
![个人信息测试数据 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoTestData.png?raw=true)

##### PostMan测试:
![个人信息导入测试 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoImportTest.png?raw=true)

##### 数据库验证:
![个人信息数据库 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoDataBase.png?raw=true)

#### 2、毕业信息导入测试

##### 模拟数据:
![毕业信息测试数据 icon](https://github.com/Cyanss/excel/blob/master/github/image/GraduateInfoTestData.png?raw=true)

##### PostMan测试:
![毕业信息导入测试 icon](https://github.com/Cyanss/excel/blob/master/github/image/GraduateInfoImportTest.png?raw=true)

##### 数据库验证:
###### 注：数据库之前没做覆盖，就存在很多重复数据，手动删除了一些，又重新导入，所以id不连续
![数据库验证 icon](https://github.com/Cyanss/excel/blob/master/github/image/GraduateInfoDataBase.png?raw=true)

### 2、导出测试
&emsp;&emsp;数据导出测试没有返回结果体，直接返回Response,会将生成好的excel文件直接以字节流的形式写入到Response，通过设置Response的Header属性让浏览器自行解析文件。

关于请求头相关知识请自行[度娘](https://www.baidu.com)
```json
{
  "Content-Disposition":"attachment;filename=文件名.xlsx",
  "Content-Type": "application/octet-stream"
}
```

#### 1、个人信息导出测试
![个人信息导出测试 icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoExportTest.png?raw=true)

#### 2、毕业信息导出测试
![毕业信息导出测试 icon](https://github.com/Cyanss/excel/blob/master/github/image/GraduateInfoExportTest.png?raw=true)

#### 3、分类信息导出测试
&emsp;&emsp;按性别导出主要是为了满足，不同的数据分别写入到同一个excel的不同sheet中的这个需求。

##### 按性别分类导出（男）:
![按性别分类导出（男） icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoDistinguishWithSex(Male).png?raw=true)

##### 按性别分类导出（女）:
![按性别分类导出（女） icon](https://github.com/Cyanss/excel/blob/master/github/image/UserInfoDistinguishWithSex(Female).png?raw=true)

#### 4、详细信息导出测试
&emsp;&emsp;详细信息导出用到了联表Join,用原生SQL查询的话这其实不算个问题，我这里最想展示的是当用Jpa时，分页+动态sql+联表Join的较为复杂的实现方式。

```java
public class GraduateUserJointRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 动态SQL查询数据分页
     * @param whereSql
     * @param pageable
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<GraduateUserJoint> findAllByGraduateUserJointWithWhereSql(String whereSql, Pageable pageable) {
        /** 原生SQL语句结合外部动态构建的whereSql实现动态SQL拼接(在这里拼也不是不可以) */
        /** 联表Join时根据需求确定 left/right/inner */
        /** whereSql 中最少要包含 1=1 类似的逻辑表达式 防止SQL报错 */
        String dataSql = "select * from graduate_info g inner join user_info u on u.user_id = g.user_id where " + whereSql;
        String countSql = "select count(1) from graduate_info g inner join user_info u on u.user_id = g.user_id where " + whereSql;
        
        Query dataQuery = entityManager.createNativeQuery(dataSql, GraduateUserJoint.class);
        Query countQuery = entityManager.createNativeQuery(countSql);
        /** 分页供能实现 */
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        /** 分页总数统计 */
        BigInteger count = (BigInteger) countQuery.getSingleResult();
        long total = count.longValue();
        /** 分页数据 */
        List<GraduateUserJoint> graduateUserJointList = total > pageable.getOffset() ? dataQuery.getResultList() : Collections.<GraduateUserJoint> emptyList();
        return new PageImpl<>(graduateUserJointList, pageable, total);
    }
    //...
}
```

##### 存在bug的详细信息导出：
![详细信息导出测试 icon](https://github.com/Cyanss/excel/blob/master/github/image/DetailInfoExportTest(Bug).png?raw=true)

##### 修复bug的详细信息导出：
![详细信息导出测试（修复） icon](https://github.com/Cyanss/excel/blob/master/github/image/DetailInfoExportTest(Repair).png?raw=true)

[EasyExcel](https://github.com/alibaba/easyexcel)  
[Spring-Boot](https://spring.io/projects/spring-boot/)  
[Spring-Data-Jpa](https://spring.io/projects/spring-data-jpa)
    
