# ZoomSecurity数据初始化

使用flyway实现数据库语句初始化功能，使用配置zoom.security.db-init=true来开启数据初始化，不与默认flyway冲突，即你自己的项目也可以使用默认flyway来管理数据库语句。

| 配置         | 值                           | 备注                                                         |
| ------------ | ---------------------------- | ------------------------------------------------------------ |
| 语句路径     | db/zoom/security             | 后期可能加上mysql/oracle等来区分数据库语句                   |
| flyway表格名 | flyway_zoom_security_history | 与默认表名不同，防止版本冲突                                 |
| 数据库配置   | ${spring.datasource}         | 使用spring.datasource配置，包括url，username，password，driver-class-name |

## 表

用户表：cm_person；角色表： sm_role；权限表：sm_permission；

关系表：sr_person_role，sr_role_permission；

组织机构：cm_orgnization；用户组织关系（不急）；角色组织关系表：（数据权限）

字典类型：dm_dicttype；字典值：dm_dictvalue；

变更记录表：

> 权限包含菜单和功能，字典值引用字典类型。

