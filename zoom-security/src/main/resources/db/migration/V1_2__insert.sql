insert into cm_person
(id, code, name, creator_id, create_date, modifier_id, modify_date, password) values
(1, 'root', '超级管理员', 1, now(), 1, now(), '{bcrypt}$2a$10$wRfzXGlZb2PW63Usybr9tuxA8x2RLKkSUjRF8RdTWelEDWBctR7YW'),
(2, 'admin', '管理员', 1, now(), 1, now(), '{bcrypt}$2a$10$oR96Ugkr8s6zFJeQ9nh2/OQ4U4uV2Bac1cBF.odOR24Kwm3.1DG56'),
(3, 'test', '测试用户', 1, now(), 1, now(), '{bcrypt}$2a$10$yDhzCY2V5aaHlqezkD92NeSZmthEzeMlgz.qOvkm4FfG1YgR66Ruu')
;

insert into sm_role
(id, code, name, creator_id, create_date, modifier_id, modify_date) values
(1, 'admin', '管理员', 1, now(), 1, now()),
(2, 'user', '用户', 1, now(), 1, now()),
(3, 'temp', '临时用户', 1, now(), 1, now())
;

insert into sr_person_role
(id, person_id, role_id, creator_id, create_date, modifier_id, modify_date) values
(1, 1, 1, 1, now(), 1, now()),
(2, 1, 2, 1, now(), 1, now()),
(3, 2, 1, 1, now(), 1, now()),
(4, 2, 2, 1, now(), 1, now()),
(5, 3, 2, 1, now(), 1, now())
;

insert into sm_permission
(id, code, name, parent_id, sort, creator_id, create_date, modifier_id, modify_date) values
(1, 'home', '首页', null, 1, 1, now(), 1, now()),
(2, 'admin', '管理员', null, 2, 1, now(), 1, now())
;

insert into sr_role_permission
(id, role_id, permission_id, creator_id, create_date, modifier_id, modify_date) values
(1, 2, 1, 1, now(), 1, now()),
(2, 1, 2, 1, now(), 1, now())
;
