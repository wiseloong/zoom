create table cm_person (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   password             varchar(255) comment '密码',
   sex                  bigint comment '性别',
   birthday             datetime comment '出生日期',
   picture              varchar(255) comment '照片',
   phone                varchar(255) comment '电话',
   mail                 varchar(255) comment '邮箱'
) auto_increment = 10000 comment = '用户表';

create table sm_role (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户'
) auto_increment = 10000 comment = '角色表';

create table sm_permission (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   parent_id            bigint comment '父级权限',
   icon                 varchar(255) comment '图标',
   url                  varchar(255) comment '接口地址',
   path                 varchar(255) comment '接口地址',
   sort                 int comment '排序',
   type_id              bigint comment '类型'
) auto_increment = 10000 comment = '权限表';

create table sm_resource (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   url                  varchar(255) comment '接口地址',
   type_id              bigint comment '类型'
) auto_increment = 10000 comment = '资源表';

create table sr_person_role (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   person_id            bigint comment '用户id',
   role_id              bigint comment '角色id'
) auto_increment = 10000 comment = '用户角色关系表';

create table sr_role_permission (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   role_id              bigint comment '角色id',
   permission_id        bigint comment '权限id'
) auto_increment = 10000 comment = '角色权限关系表';

create table sr_permission_resource (
   id                   bigint primary key auto_increment comment 'id',
   code                 varchar(255) comment '编码',
   name                 varchar(255) comment '名称',
   creator_id           bigint comment '创建人',
   create_date          datetime comment '创建时间',
   modifier_id          bigint comment '修改人',
   modify_date          datetime comment '修改时间',
   is_valid             tinyint default 1 comment '有效性',
   is_delete            tinyint default 0 comment '是否删除',
   notes                varchar(4000) comment '备注',
   version              int default 1 comment '版本',
   tenant_id            bigint comment '租户',
   permission_id        bigint comment '权限id',
   resource_id          bigint comment '资源id'
) auto_increment = 10000 comment = '权限资源关系表';
