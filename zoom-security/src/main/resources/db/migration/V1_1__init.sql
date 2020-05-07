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
   version              int comment '版本',
   tenant_id            bigint comment '租户'
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
   version              int comment '版本',
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
   version              int comment '版本',
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
   version              int comment '版本',
   tenant_id            bigint comment '租户',
   url                  varchar(255) comment '接口地址',
   type_id              bigint comment '类型'
) auto_increment = 10000 comment = '资源表';
