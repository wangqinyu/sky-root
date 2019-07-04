# sky-root

# 1、环境
## 硬件
- Linux

## 软件
- 数据库使用mysql5.6.5+
- 缓存数据库使用redis
- Spring-Cloud 使用Greenwich.SR1版本
- Spring-Boot 使用 2.1.3.RELEASE版本

# 2、架构设计

- 采用SpringCloud + SpringBoot架构的微服务
- eureka-server:使用Eureka服务中心，管理所有服务
- sky-gateway:网关采用Gateway进行路由拦截和过滤（尚未配置过滤）
- sky-server:主服务端（同时作为eureka-client）
- sky-adapter-xxx:适配器
- 微服务之间采用Feign调用（阿里云短信和微信除外）
- 微服务间服务采用共享 spring-session（非web session），session持久化至redis中【注：升级策略采取spring security oauth2 + jwt （token）+ gateway】
- 日志采用slf4j+logback
- 配置采用spring-config 统一配置（尚未实现，暂时各自配置）
- 接口文档及展示采用swagger
- 微服务使用熔断器
- 数据存储使用spring-data-jpa架构，避免在程序中使用原生SQL
   - 简单查询：使用JPA-DAO驼峰命名构造方法查询，注意避免一些属性和数据库保留关键字冲突
   - 简单查询：使用Example实例构造查询方法
   - 复杂查询：使用Specification条件构造器构造查询
   - 复杂查询：使用JPQL语句进行查询

# 3、实体模型

> 用户信息 UserInfo

```mysql
CREATE TABLE `tb_user_info` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL DEFAULT '1',
  `user_guid` varchar(255) NOT NULL,
  `user_pid` bigint(20) DEFAULT NULL,
  `user_status` int(11) NOT NULL DEFAULT '0',
  `user_auth` bigint(20) DEFAULT NULL,
  `user_type` int(11) DEFAULT '0',
  `user_name` varchar(128) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `salt` varchar(500) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `score` bigint(20) NOT NULL DEFAULT '0',
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_uer_info_guid` (`user_guid`),
  UNIQUE KEY `idx_uer_info_user_name` (`user_name`),
  UNIQUE KEY `idx_uer_info_mail` (`mail`),
  UNIQUE KEY `idx_uer_info_phone` (`phone`),
  KEY `idx_uer_info_status` (`user_status`),
  KEY `idx_uer_info_createdAt` (`createdAt`),
  KEY `idx_uer_info_pid` (`user_pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8
```

>  项目信息  ProjectInfo

```mysql
CREATE TABLE `tb_project_info` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL DEFAULT '1',
  `project_status` int(11) NOT NULL DEFAULT '1',
  `project_name` varchar(255) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `content` text,
  `project_desc` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `left_amount` bigint(20) NOT NULL,
  `max_amount` bigint(20) DEFAULT NULL,
  `min_amount` bigint(20) DEFAULT NULL,
  `post_flag` int(11) NOT NULL,
  `url_gen` varchar(500) NOT NULL,
  `url_type` int(11) NOT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `idx_project_info_project_name` (`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
```
> 任务信息 TaskInfo

```mysql
CREATE TABLE `tb_task_info` (
  `task_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL DEFAULT '1',
  `task_pid` bigint(20) DEFAULT '-1',
  `user_id` bigint(20) DEFAULT NULL,
  `user_guid` varchar(255) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `task_status` int(11) NOT NULL DEFAULT '0',
  `task_amount` bigint(20) NOT NULL,
  `task_left` bigint(20) NOT NULL,
  `task_benefit` bigint(20) DEFAULT NULL,
  `task_benefit_actual` bigint(20) DEFAULT NULL,
  `task_report` bigint(20) DEFAULT NULL,
  `task_confirm` bigint(20) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`),
  KEY `project_id` (`project_id`),
  KEY `idx_tb_task_info_user_id` (`user_id`),
  KEY `idx_tb_task_info_user_guid` (`user_guid`),
  KEY `idx_task_info_project_id_and_status` (`project_id`,`task_status`),
  CONSTRAINT `project_task_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `tb_project_info` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
```
> 任务领取历史信息 TaskHistory

```mysql
CREATE TABLE `tb_task_history` (
  `history_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
```
> 任务数据结果信息 TaskResult

```mysql
CREATE TABLE `tb_project_task_result` (
  `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_guid` varchar(255) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  `step_index` int(11) NOT NULL,
  `task_time` datetime NOT NULL,
  `amount` bigint(20) NOT NULL DEFAULT '0',
  `result_type` int(11) NOT NULL DEFAULT '0',
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`result_id`),
  UNIQUE KEY `idx_tb_project_task_result_main` (`user_id`,`project_id`,`step_index`,`task_time`) USING BTREE,
  KEY `idx_tb_project_task_result_user_guid` (`user_guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
> 任务标签信息 TaskStep 
  
```mysql
CREATE TABLE `tb_project_task_step` (
  `step_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `step_index` int(11) NOT NULL,
  `step_name` varchar(100) NOT NULL,
  `step_alias` varchar(100) NOT NULL,
  `step_extra` varchar(500) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`step_id`),
  KEY `idx_tb_project_task_step_project_id` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8
```
> 用户日志信息 UserLog

```mysql
CREATE TABLE `tb_user_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `relay_id` bigint(20) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `extra` text,
  `ip` varchar(20) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8
```

> 定时任务相关信息(官方默认11张表)

```sql
 - QRTZ_BLOB_TRIGGERS
 - QRTZ_CALENDARS
 - QRTZ_CRON_TRIGGERS
 - QRTZ_FIRED_TRIGGERS
 - QRTZ_JOB_DETAILS
 - QRTZ_LOCKS
 - QRTZ_PAUSED_TRIGGER_GRPS
 - QRTZ_SCHEDULER_STATE
 - QRTZ_SIMPLE_TRIGGERS
 - QRTZ_SIMPROP_TRIGGERS
 - QRTZ_TRIGGERS
```

# 4、服务接口

## 4.1 注意

 - 接口分为管理端接口 和业务接口，管理端接口不对外使用，业务接口可对外使用；
 - 接口入参采用最大包含量，出参采用统一数据模型
 - 查询数据时会对敏感信息进行处理后返回
 - 时间类字段统一按照格式进行传输和显示（如yyyy-MM-dd hh:mm:ss）
 - 为保证性能，尽可能使用分页查询；若请求参数不包含分页排序对象，则默认查询全部结果
 - 条件查询时，若无请求参数则查询全部结果
 - 可选条件查询时，若无请求参数则返回空

## 4.2 接口：对外使用接口

> 公共返回值

- 返回值结构

参数名|必填性|类型|可选值|备注
----|----|----|----|------
code|yes|String|200/404……|返回状态标记
message|yes|String|-|返回状态含义
data|no|Object|-|返回值对象

> 公共请求参数

- 分页排序对象模型

参数名|必填性|类型|可选值|备注
----|----|----|----|------
sort|no|Map<String,String>集合|ASC/DESC|排序集合，其中 key：排序字段；value：排序方向（ASC正序、DESC倒序）
size|no|Integer|10/20/……|分页尺寸（不作具体数值限制）
page|no|Integer|1/2/3……|页码（不作具体数值限制）

#### 校验接口API（/check）

> 1、发送短信验证码 /sendSMS

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
phone|yes|String|-|手机号码

> 2、查询短信验证码（该接口非业务必须，酌情使用） /querySMS

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
phone|yes|String|-|手机号码
BizId|yes|String|-|发送回执Id

> 3、获取图形验证码（BASE64编码，GIF格式） /getCaptcha

- 请求参数：无



### 用户管理接口API（/user）

> 1、用户注册 /register

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userPid|yes|String|-|上级用户Id
type|yes|Integer|1/2/4/8|用户类型：1表示平台；2表示总公司；4表示分公司；8表示推广员
name|yes|String|大小写字母和数字，长度5-20|用户账号，必须以字母开头
password|yes|String|大小写字母和数字，长度6-20|密码
realName|no|String|不超过30个字符|用户姓名
phone|yes|String|符合13位手机号码规则|大陆手机号码
mail|no|String|符合邮箱规则|-
smsCaptchaId|yes|String|-|短信验证码Id
smsCaptcha|yes|String|-|短信验证码
picCaptchaId|yes|String|-|图形验证码Id
picCaptcha|yes|String|-|图形验证码

> 2、用户登录 /login

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
loginType|yes|Integer|1/2|登录方式（2选1）：1表示用户名密码登录；2表示短信验证码登录
picCaptchaId|yes|String|-|图形验证码Id
picCaptcha|yes|String|-|图形验证码
name|loginType为1时yes|String|大小写字母和数字，长度5-20|用户账号，必须以字母开头
password|loginType为1时yes|String|大小写字母和数字，长度6-20|密码
phone|loginType为2时yes|String|符合13位手机号码规则|大陆手机号码
smsCaptchaId|loginType为2时yes|String|-|短信验证码Id
smsCaptcha|loginType为2时yes|String|-|短信验证码


> 3、获取用户信息 /getUserInfo

- 请求参数：无

> 4、登出 /logout

- 请求参数：无

> 5、更新用户数据 /updateUserInfo

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|yes|Long|-|用户Id
realName|no|String|-|用户姓名
mail|no|String|符合邮箱规则|-

> 6、修改登录密码 /resetPassword

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
passwordOld|yes|String|大小写字母和数字，长度6-20|密码
passwordNew|yes|String|大小写字母和数字，长度6-20|密码

> 7、忘记密码 /fogetPassword

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
phone|yes|String|符合13位手机号码规则|大陆手机号码
smsCaptchaId|yes|String|-|短信验证码Id
smsCaptcha|yes|String|-|短信验证码
password|yes|String|大小写字母和数字，长度6-20|密码

> 8、更改手机号码 /resetPhone

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
phone|yes|String|符合13位手机号码规则|大陆手机号码
smsCaptchaId|yes|String|-|短信验证码Id
smsCaptcha|yes|String|-|短信验证码

> 9、更改下一级子用户状态 /resetNextChildMemberStatus

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|yes|Long|-|（要更改的）用户Id
userPid|yes|String|-|上级用户Id（当前登录用户）
status|yes|Integer|0/1/2|用户状态：0 未激活 1 已激活 2 禁用

> 10、查询下一级子成员列表 /getNextChildMembers

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）


> 11、获取推广二维码（BASE64编码，PNG/JPG格式） /getQRCode

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|yes|Long|-|用户Id
projectId|yes|Long|-|项目Id

#### 项目管理接口API（/project）

> 1、查询全部项目（状态：2 已上线） /findAllOnline

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

> 2、获取项目信息 /getProjectInfo

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
projectId|yes|Long|-|项目Id

#### 任务管理接口API（/task）

> 1、领取任务 /claimTask

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|yes|Long|-|用户Id
projectId|yes|Long|-|项目Id
claimAmount|yes|Long|-|当前领取数量
taskPid|no(用户类型4/8必填)|Long|-|上级任务Id

> 2、获取任务信息 /getTaskInfo

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
taskId|yes|Long|-|任务Id

> 3、条件查询(AND) /findByParams

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
taskId|no|Long|-|任务Id
taskPid|no|Long|-|上级任务Id
userId|no|Long|-|用户Id
userGuid|no|String|-|用户Id，对外使用
projectId|no|Long|-|项目Id
status|no|Integer|1/2/3/9|任务状态：1.进行中 2.已提交 3.取消 9.完结
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

> 4、更改任务状态 /resetTaskStatus

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
taskId|no|Long|-|任务Id
status|no|Integer|1/2/3/9|任务状态：1.进行中 2.已提交 3.取消 9.完结

#### 任务历史管理接口API（/history）

> 1、条件查询（AND） /findByParams

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|no|Long|-|用户Id
projectId|no|Long|-|项目Id
taskId|no|Long|-|任务Id
amount|no|Long|-|领取任务数量
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

#### 任务结果管理接口API（/result）

> 1、条件查询（AND） /findByParams

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|no|Long|-|用户Id
userGuid|no|String|-|用户Id，对外使用
projectId|no|Long|-|项目Id
taskId|no|Long|-|任务Id
index|no|Integer|1/2/3/4……|任务标签
taskTime_start|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新起始时间
taskTime_end|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新截止时间
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

> 2、可选条件查询（OR） /findByOptional

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|no|Long|-|用户Id
userGuid|no|String|-|用户Id，对外使用
projectId|no|Long|-|项目Id
taskId|no|Long|-|任务Id
index|no|Integer|1/2/3/4……|任务标签
taskTime_start|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新起始时间
taskTime_end|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新截止时间
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

> 3、统计任务结果量 /count

- 请求参数：无

> 4、根据条件统计任务结果量（AND） /countByParams

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
userId|no|Long|-|用户Id
userGuid|no|String|-|用户Id，对外使用
projectId|no|Long|-|项目Id
taskId|no|Long|-|任务Id
index|no|Integer|1/2/3/4……|指标序号
taskTime_start|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新起始时间
taskTime_end|no|Date|格式yyyy-MM-dd HH:mm:ss|任务刷新截止时间

#### 任务指标管理接口API（/step）

> 1、条件查询（AND） /findByParams

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
projectId|no|Long|-|项目Id
index|no|Integer|1/2/3/4……|指标序号
name|no|String|-|指标名称
alias|no|String|-|指标别名
pageAndSortParams|no|Object|-|分页排序对象（详见公共请求入参）

> 2、获取任务指标信息 /getTaskStep

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
taskId|yes|Long|-|任务Id

#### 二维码接口API（/qrcode）

> 1、生成二维码（BASE64编码，PNG/JPG格式） /drawQRCode

- 请求参数

参数名|必填性|类型|可选值|备注
----|----|----|----|------
content|yes|String|-|二维码内容
size|no|Integer|200/500……|尺寸（对数值不做限制，默认200px）
bgColor|no|Integer（16进制）|如黑色0xFF000000|背景颜色(16进制0x表示，默认黑色)
preColor|no|Integer（16进制）|如白色0xFFFFFFFF|前景颜色(16进制0x表示，默认白色)
style|no|String|RECT/CIRCLE/TRIANGLE/DIAMOND/SEXANGLE/OCTAGON|样式形状(RECT：矩形，CIRCLE：圆点，TRIANGLE：三角形，DIAMOND：五边形，SEXANGLE：六边形，OCTAGON：八边形；默认矩形)

# 5、编码注意
  - 代码中不允许出现非系统静态常量，所有常量在constant中注明
  - 异常使用统一异常处理类进行处理封装
  - 命名规则采用驼峰式+英文
  - 采用抽象和多态方式进行编码，易于扩展和更改
  - 服务几适配器调用采用统一格式

# 6、TODO List
* [x]  用户B管理模块
* [x]  项目管理模块
* [x]  任务管理模块
* [x]  分销推广模块
* [x]  数据查看模块
* [x]  工具模块：二维码小工具
* [x]  kidcrm适配器服务
* [ ]  管理平台
* [x]  数据查看模块升级：查询子用户数据
* [ ]  文件上传下载
* [ ]  配置中心
* [ ]  定时任务模块化
* [ ]  用户A管理模块
* [ ]  数据报表及大屏模块
* [ ]  统一支付模块：微信支付宝
* [ ]  分销结算模块
* [ ]  工具模块：活动报名小工具
* [ ]  工具模块：活动投票小工具
* [ ]  aliyun适配器服务
* [ ]  日志模块
* [ ]  自动化集成测试运维中心

# 7、名词解释

- 用户A：发布项目的用户（公司主体），查看项目进展情况
- 用户B：执行任务的用户（公司主体及个体），查看项目和任务情况
- 平台管理方：对平台各业务进行操作和监管
- 工具模块：为用户提供一些推广所需的小工具，帮助用户建立推广生态
- 适配器：对接的每一家公司相当于一个适配器，该适配器负责接口的调用
- 日志：所有用户和系统访问和操作的日志记录
- 自动化集成测试运维中心：为软件测试人员、软件维护人员提供一个平台，方便其操作
- 定时任务：按照提前设定的时间周期获取第三方用户A的数据
- 配置中心：所有系统和模块的配置数据合并到一个平台上，方便开发、测试、运维人员工作
- 数据报表及大屏：为运营人员和市场人员提供数据看板
- 分销结算：平台自助向用户B个体进行财务结算服务，无需用户B主体分别结算每个子用户
- 分销推广：为用户提供分销推广的功能，用户可以生成私有二维码等
- 数据查看：用户可以查看自己的推广数据