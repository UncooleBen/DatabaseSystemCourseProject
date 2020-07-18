INSERT INTO `t_user`
VALUES (1, 'admin', 'admin', 'Juntao Peng', 'MALE', 0, '13918300751');
INSERT INTO `t_user`
VALUES (2, 'user', 'user', 'Juntao Peng', 'MALE', 1, '13918300751');

INSERT INTO `t_building`
VALUES (1, '理科大楼', '位于中山北路校区的理科大楼', 9999.00);
INSERT INTO `t_building`
VALUES (2, '河西食堂', '位于中山北路校区的河西食堂', 1000.00);
INSERT INTO `t_building`
VALUES (3, '文史楼', '位于中山北路校区的文史楼', 10000.00);
INSERT INTO `t_building`
VALUES (4, '共青场地', '位于中山北路校区的共青体育场', 100000.00);

INSERT INTO `t_comment` VALUES (0, 2, 1, TIMESTAMP('2020-06-03'), '很好！', 1);
INSERT INTO `t_comment` VALUES (0, 2, 2, TIMESTAMP('2020-06-03'), '很好！', 1);
INSERT INTO `t_comment` VALUES (0, 2, 3, TIMESTAMP('2020-06-03'), '很好！', 1);
INSERT INTO `t_comment` VALUES (0, 2, 4, TIMESTAMP('2020-06-03'), '很好！', 1);
INSERT INTO `t_record` (`time`, `start_date`, `end_date`, `user_id`, `building_id`, `verified`) VALUES (TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-02'), TIMESTAMP('2020-06-03'), 2, 1, 1);
INSERT INTO `t_record` (`time`, `start_date`, `end_date`, `user_id`, `building_id`, `verified`) VALUES (TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-02'), TIMESTAMP('2020-06-03'), 2, 2, 1);
INSERT INTO `t_record` (`time`, `start_date`, `end_date`, `user_id`, `building_id`, `verified`) VALUES (TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-02'), TIMESTAMP('2020-06-03'), 2, 3, 1);
INSERT INTO `t_record` (`time`, `start_date`, `end_date`, `user_id`, `building_id`, `verified`) VALUES (TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-02'), TIMESTAMP('2020-06-03'), 2, 4, 1);

INSERT INTO `t_news` (`title`, `created`, `last_modified`, `author`, `detail`) VALUES ('理科大楼发生化学物品泄漏！', TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-01'), '管理员', '理科大楼X楼实验室发生化学物品泄漏。');
INSERT INTO `t_news` (`title`, `created`, `last_modified`, `author`, `detail`) VALUES ('学校毕业典礼如期举行！', TIMESTAMP('2020-06-24'), TIMESTAMP('2020-06-24'), '管理员', '两校区分设会场');
INSERT INTO `t_news` (`title`, `created`, `last_modified`, `author`, `detail`) VALUES ('疫情期间校门管制！', TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-01'), '管理员', '如题');
INSERT INTO `t_news` (`title`, `created`, `last_modified`, `author`, `detail`) VALUES ('图书馆恢复开放！', TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-01'), '管理员', '如题');
INSERT INTO `t_news` (`title`, `created`, `last_modified`, `author`, `detail`) VALUES ('河西食堂新菜品！', TIMESTAMP('2020-06-01'), TIMESTAMP('2020-06-01'), '管理员', '如题');